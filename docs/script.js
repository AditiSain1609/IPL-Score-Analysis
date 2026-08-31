/* ==========================================
   IPL SCORE ANALYSIS - WEB APPLICATION
   Reads matches.csv and deliveries.csv
========================================== */

let matches = [];
let deliveries = [];


/* ==========================================
   CSV PARSER
========================================== */

function parseCSV(text) {

    const rows = [];
    let row = [];
    let value = "";
    let insideQuotes = false;

    for (let i = 0; i < text.length; i++) {

        const char = text[i];
        const next = text[i + 1];

        if (char === '"' && insideQuotes && next === '"') {
            value += '"';
            i++;
        }

        else if (char === '"') {
            insideQuotes = !insideQuotes;
        }

        else if (char === "," && !insideQuotes) {
            row.push(value.trim());
            value = "";
        }

        else if ((char === "\n" || char === "\r") && !insideQuotes) {

            if (char === "\r" && next === "\n") {
                i++;
            }

            row.push(value.trim());

            if (row.some(cell => cell !== "")) {
                rows.push(row);
            }

            row = [];
            value = "";
        }

        else {
            value += char;
        }
    }

    if (value !== "" || row.length > 0) {
        row.push(value.trim());

        if (row.some(cell => cell !== "")) {
            rows.push(row);
        }
    }

    if (rows.length === 0) {
        return [];
    }

    const headers = rows[0].map(header =>
        header.trim().toLowerCase()
    );

    return rows.slice(1).map(row => {

        const object = {};

        headers.forEach((header, index) => {
            object[header] = row[index] || "";
        });

        return object;
    });
}


/* ==========================================
   LOAD CSV FILE
========================================== */

async function loadCSV(filePath) {

    const response = await fetch(filePath);

    if (!response.ok) {
        throw new Error(
            "Unable to load " + filePath
        );
    }

    const text = await response.text();

    return parseCSV(text);
}


/* ==========================================
   LOAD IPL DATA
========================================== */

async function loadIPLData() {

    try {

        console.log("Loading IPL data...");

        matches = await loadCSV("../data/matches.csv");

        deliveries = await loadCSV("../data/deliveries.csv");

        console.log("Matches loaded:", matches.length);
        console.log("Deliveries loaded:", deliveries.length);

        updateDashboard();

        showTopBatsmen();

        showTopBowlers();

        showTeamWins();

        console.log("IPL data loaded successfully!");

    }

    catch (error) {

        console.error(error);

        showError(
            "Unable to load IPL data. Please run the website using a local server."
        );
    }
}


/* ==========================================
   DASHBOARD
========================================== */

function updateDashboard() {

    const totalMatches =
        matches.length;

    const totalRuns =
        deliveries.reduce(
            (sum, delivery) =>
                sum + number(delivery.total_runs),
            0
        );


    const players = new Set();

    deliveries.forEach(delivery => {

        if (delivery.batsman) {
            players.add(delivery.batsman);
        }

        if (delivery.bowler) {
            players.add(delivery.bowler);
        }

    });


    const teams = new Set();

    matches.forEach(match => {

        if (match.team1) {
            teams.add(match.team1);
        }

        if (match.team2) {
            teams.add(match.team2);
        }

    });


    setText(
        "totalMatches",
        totalMatches
    );

    setText(
        "totalRuns",
        totalRuns.toLocaleString()
    );

    setText(
        "totalPlayers",
        players.size
    );

    setText(
        "totalTeams",
        teams.size
    );
}


/* ==========================================
   TOP 10 BATSMEN
========================================== */

function getTopBatsmen() {

    const batsmanStats = {};


    deliveries.forEach(delivery => {

        const batsman =
            delivery.batsman;

        if (!batsman) {
            return;
        }


        if (!batsmanStats[batsman]) {

            batsmanStats[batsman] = {
                runs: 0,
                matches: new Set()
            };

        }


        batsmanStats[batsman].runs +=
            number(delivery.batsman_runs);


        if (delivery.match_id) {

            batsmanStats[batsman]
                .matches
                .add(delivery.match_id);

        }

    });


    return Object.entries(batsmanStats)

        .map(([player, stats]) => ({

            player: player,

            runs: stats.runs,

            matches: stats.matches.size

        }))

        .sort((a, b) => b.runs - a.runs)

        .slice(0, 10);
}


/* ==========================================
   DISPLAY TOP BATSMEN
========================================== */

function showTopBatsmen() {

    const table =
        document.getElementById(
            "batsmenTable"
        );


    if (!table) {
        return;
    }


    const batsmen =
        getTopBatsmen();


    table.innerHTML = "";


    if (batsmen.length === 0) {

        table.innerHTML =
            `<tr>
                <td colspan="5">
                    No batting data found
                </td>
            </tr>`;

        return;
    }


    batsmen.forEach((batsman, index) => {

        const strikeRate =
            calculateStrikeRate(
                batsman.player
            );


        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>${index + 1}</td>

            <td>
                <strong>${escapeHTML(
            batsman.player
        )}</strong>
            </td>

            <td>
                ${batsman.runs.toLocaleString()}
            </td>

            <td>
                ${batsman.matches}
            </td>

            <td>
                ${strikeRate}
            </td>

        `;


        table.appendChild(row);

    });
}


/* ==========================================
   STRIKE RATE
========================================== */

function calculateStrikeRate(player) {

    let runs = 0;
    let balls = 0;


    deliveries.forEach(delivery => {

        if (delivery.batsman !== player) {
            return;
        }


        runs +=
            number(delivery.batsman_runs);


        /*
           Wide balls are not counted
           as legal balls faced.
        */

        if (
            number(delivery.wide_runs) === 0
        ) {
            balls++;
        }

    });


    if (balls === 0) {
        return "0.00";
    }


    return (
        (runs / balls) * 100
    ).toFixed(2);
}


/* ==========================================
   TOP 10 BOWLERS
========================================== */

function getTopBowlers() {

    const bowlerStats = {};


    deliveries.forEach(delivery => {

        const bowler =
            delivery.bowler;


        if (!bowler) {
            return;
        }


        if (!bowlerStats[bowler]) {

            bowlerStats[bowler] = {

                wickets: 0,

                runsConceded: 0,

                legalBalls: 0

            };

        }


        /*
           Bowler conceded runs:

           batsman runs +
           wides +
           no-balls

           Byes and leg-byes are not
           charged to the bowler.
        */

        const batsmanRuns =
            number(
                delivery.batsman_runs
            );

        const wideRuns =
            number(
                delivery.wide_runs
            );

        const noBallRuns =
            number(
                delivery.noball_runs
            );


        bowlerStats[bowler]
            .runsConceded +=
            batsmanRuns +
            wideRuns +
            noBallRuns;


        /*
           Wicket calculation
        */

        const dismissal =
            (
                delivery.dismissal_kind ||
                ""
            ).toLowerCase();


        const validWicketKinds = [

            "bowled",

            "caught",

            "caught and bowled",

            "lbw",

            "stumped",

            "hit wicket"

        ];


        if (
            validWicketKinds.includes(
                dismissal
            )
        ) {

            bowlerStats[bowler]
                .wickets++;

        }


        /*
           Legal delivery
        */

        if (
            wideRuns === 0 &&
            noBallRuns === 0
        ) {

            bowlerStats[bowler]
                .legalBalls++;

        }

    });


    return Object.entries(bowlerStats)

        .map(([player, stats]) => {

            const overs =
                Math.floor(
                    stats.legalBalls / 6
                );

            const balls =
                stats.legalBalls % 6;


            let economy = "0.00";


            if (stats.legalBalls > 0) {

                economy =
                    (
                        stats.runsConceded /
                        (
                            stats.legalBalls / 6
                        )
                    ).toFixed(2);

            }


            return {

                player: player,

                wickets: stats.wickets,

                runsConceded:
                stats.runsConceded,

                overs:
                    `${overs}.${balls}`,

                economy: economy

            };

        })

        .sort((a, b) => {

            if (
                b.wickets !==
                a.wickets
            ) {

                return (
                    b.wickets -
                    a.wickets
                );

            }

            return (
                a.economy -
                b.economy
            );

        })

        .slice(0, 10);
}


/* ==========================================
   DISPLAY TOP BOWLERS
========================================== */

function showTopBowlers() {

    const table =
        document.getElementById(
            "bowlersTable"
        );


    if (!table) {
        return;
    }


    const bowlers =
        getTopBowlers();


    table.innerHTML = "";


    if (bowlers.length === 0) {

        table.innerHTML =
            `<tr>
                <td colspan="5">
                    No bowling data found
                </td>
            </tr>`;

        return;
    }


    bowlers.forEach((bowler, index) => {

        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>${index + 1}</td>

            <td>
                <strong>${escapeHTML(
            bowler.player
        )}</strong>
            </td>

            <td>
                ${bowler.wickets}
            </td>

            <td>
                ${bowler.runsConceded}
            </td>

            <td>
                ${bowler.economy}
            </td>

        `;


        table.appendChild(row);

    });
}


/* ==========================================
   TEAM WINS
========================================== */

function getTeamWins() {

    const wins = {};


    matches.forEach(match => {

        const winner =
            match.winner;


        if (!winner) {
            return;
        }


        if (!wins[winner]) {
            wins[winner] = 0;
        }


        wins[winner]++;

    });


    return Object.entries(wins)

        .map(([team, wins]) => ({

            team: team,

            wins: wins

        }))

        .sort(
            (a, b) =>
                b.wins - a.wins
        );
}


/* ==========================================
   DISPLAY TEAM WINS
========================================== */

function showTeamWins() {

    const container =
        document.getElementById(
            "teamWins"
        );


    if (!container) {
        return;
    }


    const teams =
        getTeamWins();


    container.innerHTML = "";


    teams.forEach(team => {

        const card =
            document.createElement("div");


        card.className =
            "team-card";


        card.innerHTML = `

            <h3>
                ${escapeHTML(
            team.team
        )}
            </h3>

            <p>Matches Won</p>

            <div class="wins">
                ${team.wins}
            </div>

        `;


        container.appendChild(card);

    });
}


/* ==========================================
   SCROLL BUTTON
========================================== */

function scrollToDashboard() {

    const dashboard =
        document.getElementById(
            "dashboard"
        );


    if (dashboard) {

        dashboard.scrollIntoView({
            behavior: "smooth"
        });

    }
}


/* ==========================================
   HELPER FUNCTIONS
========================================== */

function number(value) {

    const parsed =
        parseFloat(value);


    return isNaN(parsed)
        ? 0
        : parsed;
}


function setText(id, value) {

    const element =
        document.getElementById(id);


    if (element) {
        element.textContent = value;
    }
}


function escapeHTML(value) {

    return String(value)

        .replace(/&/g, "&amp;")

        .replace(/</g, "&lt;")

        .replace(/>/g, "&gt;")

        .replace(/"/g, "&quot;")

        .replace(/'/g, "&#039;");
}


/* ==========================================
   ERROR MESSAGE
========================================== */

function showError(message) {

    console.error(message);


    const batsmenTable =
        document.getElementById(
            "batsmenTable"
        );


    const bowlersTable =
        document.getElementById(
            "bowlersTable"
        );


    if (batsmenTable) {

        batsmenTable.innerHTML =
            `<tr>
                <td colspan="5">
                    ${escapeHTML(message)}
                </td>
            </tr>`;

    }


    if (bowlersTable) {

        bowlersTable.innerHTML =
            `<tr>
                <td colspan="5">
                    ${escapeHTML(message)}
                </td>
            </tr>`;

    }

}


/* ==========================================
   START APPLICATION
========================================== */

document.addEventListener(
    "DOMContentLoaded",
    loadIPLData
);