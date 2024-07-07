using Microsoft.Maui.Controls;
using MySqlConnector;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Text.RegularExpressions;

namespace ChessBrowser
{
    internal class Queries
    {
        /// <summary>
        /// This function runs when the upload button is pressed.
        /// Given a filename, parses the PGN file, and uploads
        /// each chess game to the user's database.
        /// </summary>
        /// <param name="PGNfilename">The path to the PGN file</param>
        /// <param name="mainPage">The main page of the application</param>
        internal static async Task InsertGameData(string PGNfilename, MainPage mainPage)
        {
            // This will build a connection string to your user's database on atr,
            // assuming you've typed a user and password in the GUI
            string connection = mainPage.GetConnectionString();

            // Load and parse the PGN file
            List<ChessGame> games = PgnReader.ReadGamesFromFile(PGNfilename);
            mainPage.SetNumWorkItems(games.Count);

            using (MySqlConnection conn = new MySqlConnection(connection))
            {
                try
                {
                    // Open a connection
                    await conn.OpenAsync();

                    foreach (ChessGame game in games)
                    {
                        // Insert into the Events table
                        int eventId = await GetOrCreateEventAsync(conn, game);

                        // Insert and update the White Player
                        int whitePlayerId = await GetOrCreatePlayerAsync(conn, game.WhitePlayer, game.WhiteElo);

                        // Insert and update the Black Player
                        int blackPlayerId = await GetOrCreatePlayerAsync(conn, game.BlackPlayer, game.BlackElo);

                        // Insert into the Games table
                        string insertGameQuery = "INSERT IGNORE INTO Games (Round, Result, Moves, BlackPlayer, WhitePlayer, eID) " +
                                                 "VALUES (@Round, @Result, @Moves, @BlackPlayer, @WhitePlayer, @eID)";
                        using (MySqlCommand gameCommand = new MySqlCommand(insertGameQuery, conn))
                        {
                            gameCommand.Parameters.AddWithValue("@Round", game.Round);
                            gameCommand.Parameters.AddWithValue("@Result", game.Result.ToString());
                            gameCommand.Parameters.AddWithValue("@Moves", game.Moves);
                            gameCommand.Parameters.AddWithValue("@BlackPlayer", blackPlayerId);
                            gameCommand.Parameters.AddWithValue("@WhitePlayer", whitePlayerId);
                            gameCommand.Parameters.AddWithValue("@eID", eventId);
                            await gameCommand.ExecuteNonQueryAsync();
                        }

                        // Notify the GUI that one work item has been completed
                        await mainPage.NotifyWorkItemCompleted();
                    }
                }
                catch (Exception e)
                {
                    System.Diagnostics.Debug.WriteLine($"An error occurred: {e.Message}");
                    System.Diagnostics.Debug.WriteLine($"Stack Trace: {e.StackTrace}");
                }
            }
        }

        private static async Task<int> GetOrCreateEventAsync(MySqlConnection conn, ChessGame game)
        {
            var selectEventCommand = new MySqlCommand(
                "SELECT eID FROM Events WHERE Name = @Name AND Site = @Site AND EventDate = @EventDate", conn);
            selectEventCommand.Parameters.AddWithValue("@Name", game.EventName);
            selectEventCommand.Parameters.AddWithValue("@Site", game.Site);
            selectEventCommand.Parameters.AddWithValue("@EventDate", game.EventDate);

            var eventId = await selectEventCommand.ExecuteScalarAsync();
            if (eventId != null)
            {
                return Convert.ToInt32(eventId);
            }

            var insertEventCommand = new MySqlCommand(
                "INSERT INTO Events (Name, Site, EventDate) VALUES (@Name, @Site, @EventDate)", conn);
            insertEventCommand.Parameters.AddWithValue("@Name", game.EventName);
            insertEventCommand.Parameters.AddWithValue("@Site", game.Site);
            insertEventCommand.Parameters.AddWithValue("@EventDate", game.EventDate);
            await insertEventCommand.ExecuteNonQueryAsync();

            return (int)insertEventCommand.LastInsertedId;
        }

        private static async Task<int> GetOrCreatePlayerAsync(MySqlConnection conn, string playerName, int elo)
        {
            var selectPlayerCommand = new MySqlCommand("SELECT pID FROM Players WHERE Name = @Name", conn);
            selectPlayerCommand.Parameters.AddWithValue("@Name", playerName);

            var playerId = await selectPlayerCommand.ExecuteScalarAsync();
            if (playerId != null)
            {
                var updateEloCommand = new MySqlCommand("UPDATE Players SET Elo = GREATEST(Elo, @Elo) WHERE pID = @pID", conn);
                updateEloCommand.Parameters.AddWithValue("@Elo", elo);
                updateEloCommand.Parameters.AddWithValue("@pID", Convert.ToInt32(playerId));
                await updateEloCommand.ExecuteNonQueryAsync();
                return Convert.ToInt32(playerId);
            }

            var insertPlayerCommand = new MySqlCommand("INSERT INTO Players (Name, Elo) VALUES (@Name, @Elo)", conn);
            insertPlayerCommand.Parameters.AddWithValue("@Name", playerName);
            insertPlayerCommand.Parameters.AddWithValue("@Elo", elo);
            await insertPlayerCommand.ExecuteNonQueryAsync();

            return (int)insertPlayerCommand.LastInsertedId;
        }

        /// <summary>
        /// Queries the database for games that match all the given filters.
        /// The filters are taken from the various controls in the GUI.
        /// </summary>
        /// <param name="white">The white player, or null if none</param>
        /// <param name="black">The black player, or null if none</param>
        /// <param name="opening">The first move, e.g., "1.e4", or null if none</param>
        /// <param name="winner">The winner as "W", "B", "D", or null if none</param>
        /// <param name="useDate">True if the filter includes a date range, False otherwise</param>
        /// <param name="start">The start of the date range</param>
        /// <param name="end">The end of the date range</param>
        /// <param name="showMoves">True if the returned data should include the PGN moves</param>
        /// <param name="mainPage">The main page of the application</param>
        /// <returns>A string separated by newlines containing the filtered games</returns>
      internal static string PerformQuery(string white, string black, string opening, string winner, bool useDate, DateTime start, DateTime end, bool showMoves, MainPage mainPage)
{
    // This will build a connection string to your user's database on atr,
    // assuming you've typed a user and password in the GUI
    string connection = mainPage.GetConnectionString();

    // Build up this string containing the results from your query
    StringBuilder parsedResult = new StringBuilder();

    // Use this to count the number of rows returned by your query
    int numRows = 0;

    using (MySqlConnection conn = new MySqlConnection(connection))
    {
        try
        {
            // Open a connection
            conn.Open();

            // Generate and execute an SQL command, then parse the results into an appropriate string and return it.
            StringBuilder query = new StringBuilder("SELECT e.Name AS EventName, e.Site, e.Date, ")
                .Append("p1.Name AS WhitePlayerName, p2.Name AS BlackPlayerName, ")
                .Append("p1.Elo AS WhiteElo, p2.Elo AS BlackElo, g.Result, g.Moves ")
                .Append("FROM Events e JOIN Games g ON e.eID = g.eID ")
                .Append("JOIN Players p1 ON g.WhitePlayer = p1.pID ")
                .Append("JOIN Players p2 ON g.BlackPlayer = p2.pID WHERE TRUE");

            if (!string.IsNullOrEmpty(white))
            {
                query.Append(" AND p1.Name = @WhitePlayerName");
            }
            if (!string.IsNullOrEmpty(black))
            {
                query.Append(" AND p2.Name = @BlackPlayerName");
            }
            if (!string.IsNullOrEmpty(opening))
            {
                query.Append(" AND Moves LIKE @OpeningMove");
            }
            if (!string.IsNullOrEmpty(winner))
            {
                query.Append(" AND Result = @Result");
            }
            if (useDate)
            {
                query.Append(" AND Date >= @StartDate AND Date <= @EndDate");
            }

            using (MySqlCommand cmd = new MySqlCommand(query.ToString(), conn))
            {
                if (!string.IsNullOrEmpty(white))
                {
                    cmd.Parameters.AddWithValue("@WhitePlayerName", white);
                }
                if (!string.IsNullOrEmpty(black))
                {
                    cmd.Parameters.AddWithValue("@BlackPlayerName", black);
                }
                if (!string.IsNullOrEmpty(opening))
                {
                    cmd.Parameters.AddWithValue("@OpeningMove", $"{opening}%");
                }
                if (!string.IsNullOrEmpty(winner))
                {
                    cmd.Parameters.AddWithValue("@Result", winner);
                }
                if (useDate)
                {
                    cmd.Parameters.AddWithValue("@StartDate", start);
                    cmd.Parameters.AddWithValue("@EndDate", end);
                }

                using (MySqlDataReader reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        numRows++;
                        parsedResult.AppendLine()
                                    .AppendLine($"Event: {reader["EventName"]}")
                                    .AppendLine($"Site: {reader["Site"]}")
                                    .AppendLine($"Date: {reader["Date"]}")
                                    .AppendLine($"White: {reader["WhitePlayerName"]} ({reader["WhiteElo"]})")
                                    .AppendLine($"Black: {reader["BlackPlayerName"]} ({reader["BlackElo"]})")
                                    .AppendLine($"Result: {reader["Result"]}");

                        if (showMoves)
                        {
                            parsedResult.AppendLine($"Moves: {reader["Moves"]}");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.Diagnostics.Debug.WriteLine(e.Message);
        }
    }

    return $"{numRows} results{parsedResult}";
}
}
}
