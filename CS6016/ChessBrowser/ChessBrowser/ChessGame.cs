using System;
using System.Text;

namespace ChessBrowser
{
    public class ChessGame
    {
        public string EventName { get; set; }
        public string Site { get; set; }
        public DateTime Date { get; set; }
        public string Round { get; set; }
        public string WhitePlayer { get; set; }
        public string BlackPlayer { get; set; }
        public int WhiteElo { get; set; }
        public int BlackElo { get; set; }
        public char Result { get; set; }
        public string Moves { get; set; }
        public int EventID { get; set; }

        public ChessGame() { }

        public ChessGame(string eventName, string site, DateTime date, string round, string whitePlayer, string blackPlayer, int whiteElo, int blackElo, char result, string moves)
        {
            EventName = eventName;
            Site = site;
            Date = date;
            Round = round;
            WhitePlayer = whitePlayer;
            BlackPlayer = blackPlayer;
            WhiteElo = whiteElo;
            BlackElo = blackElo;
            Result = result;
            Moves = moves;
        }
    }
}
