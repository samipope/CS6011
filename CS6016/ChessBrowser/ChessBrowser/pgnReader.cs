using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;

namespace ChessBrowser
{
    public static class PgnReader
    {
        public static List<ChessGame> ReadGamesFromFile(string filePath)
        {
            var games = new List<ChessGame>();
            using (var reader = new StreamReader(filePath, System.Text.Encoding.GetEncoding("iso-8859-1")))
            {
                string line;
                ChessGame currentGame = null;
                var gameMoves = new List<string>();

                while ((line = reader.ReadLine()) != null)
                {
                    try
                    {
                        if (string.IsNullOrWhiteSpace(line))
                        {
                            if (currentGame != null && gameMoves.Count > 0)
                            {
                                currentGame.Moves = string.Join(" ", gameMoves);
                                games.Add(currentGame);
                                currentGame = null;
                                gameMoves.Clear();
                            }
                        }
                        else if (line.StartsWith("["))
                        {
                            if (currentGame == null)
                            {
                                currentGame = new ChessGame();
                            }
                            ProcessTagLine(line, currentGame);
                        }
                        else
                        {
                            gameMoves.Add(line.Trim());
                        }
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine($"Error processing line: {line}");
                        Console.WriteLine(ex.Message);
                    }
                }

                // Add the last game if the file doesn't end with an empty line
                if (currentGame != null && gameMoves.Count > 0)
                {
                    currentGame.Moves = string.Join(" ", gameMoves);
                    games.Add(currentGame);
                }
            }

            return games;
        }

        private static void ProcessTagLine(string line, ChessGame currentGame)
        {
            var match = Regex.Match(line, @"\[(\w+)\s+""([^""]+)""\]");
            if (match.Success)
            {
                var tag = match.Groups[1].Value;
                var value = match.Groups[2].Value;

                switch (tag)
                {
                    case "Event":
                        currentGame.EventName = value;
                        break;
                    case "Site":
                        currentGame.Site = value;
                        break;
                    case "Round":
                        currentGame.Round = value;
                        break;
                    case "White":
                        currentGame.WhitePlayer = value;
                        break;
                    case "Black":
                        currentGame.BlackPlayer = value;
                        break;
                    case "WhiteElo":
                        if (int.TryParse(value, out var whiteElo))
                        {
                            currentGame.WhiteElo = whiteElo;
                        }
                        break;
                    case "BlackElo":
                        if (int.TryParse(value, out var blackElo))
                        {
                            currentGame.BlackElo = blackElo;
                        }
                        break;
                    case "Result":
                        currentGame.Result = ParseResult(value);
                        break;
                    case "Date":
                        currentGame.Date = ParseEventDate(value);
                        break;
                }
            }
        }

        private static DateTime ParseEventDate(string dateStr)
        {
            if (DateTime.TryParseExact(dateStr, "yyyy.MM.dd", null, System.Globalization.DateTimeStyles.None, out var eventDate))
            {
                return eventDate;
            }
            return DateTime.MinValue; // default value for bad dates
        }

        private static char ParseResult(string resultString)
        {
            return resultString switch
            {
                "1-0" => 'W',
                "0-1" => 'B',
                "1/2-1/2" => 'D',
                _ => ' '
            };
        }
    }
}
