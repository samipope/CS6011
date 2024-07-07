using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;
using System.Xml.Serialization;

namespace ChessBrowser
{

static class PgnReader
{
    public static List<ChessGame> ReadGamesFromFile(string filePath)
    {
        var games = new List<ChessGame>();
        var lines = File.ReadAllLines(filePath);
        ChessGame currentGame = null;

        foreach (var line in lines)
        {
            if (string.IsNullOrWhiteSpace(line))
            {
                if (currentGame != null)
                {
                    games.Add(currentGame);
                    currentGame = null;
                }
                continue;
            }

            if (line.StartsWith("["))
            {
                if (currentGame == null)
                {
                    currentGame = new ChessGame();
                }

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
                            currentGame.WhiteElo = int.Parse(value);
                            break;
                        case "BlackElo":
                            currentGame.BlackElo = int.Parse(value);
                            break;
                        case "Result":
                            currentGame.Result = value == "1-0" ? 'W' : value == "0-1" ? 'B' : 'D';
                            break;
                        case "EventDate":
                            DateTime.TryParse(value, out var eventDate);
                            currentGame.EventDate = eventDate;
                            break;
                    }
                }
            }
            else
            {
                currentGame.Moves += line + " ";
            }
        }

        return games;
    }
}

}