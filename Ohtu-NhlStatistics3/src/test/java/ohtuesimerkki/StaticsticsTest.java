package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ohtuesimerkki.Player;

public class StaticsticsTest {

    Statistics stats;
//    PlayerReader palayerReader = new PlayerReader() {
//
//        public List<Player> getPlayers() {
//            ArrayList<Player> players = new ArrayList<Player>();
//
//            players.add(new Player("Semenko", "EDM", 4, 12));
//            players.add(new Player("Lemieux", "PIT", 45, 54));
//            players.add(new Player("Kurri", "EDM", 37, 53));
//            players.add(new Player("Yzerman", "DET", 42, 56));
//            players.add(new Player("Gretzky", "EDM", 35, 89));
//
//            return players;
//        }
//    };

    @Before
    public void setUp() {
        List<Player> lista = new ArrayList();
        lista.add(new Player("Semenko", "EDM", 4, 12));
        lista.add(new Player("Kurri", "EDM", 37, 53));
        lista.add(new Player("Gretzky", "EDM", 35, 89));
        List<Player> lista2 = new ArrayList();
        lista2.add(new Player("Gretzky", "EDM", 35, 89));
        lista2.add(new Player("Lemieux", "PIT", 45, 54));
        lista2.add(new Player("Yzerman", "DET", 42, 56));
        stats = mock(Statistics.class);
        when(stats.search("Lemieux")).thenReturn(new Player("Lemieux", "PIT", 45, 54));
        when(stats.team("EDM")).thenReturn(lista);
        when(stats.topScorers(2)).thenReturn(lista2);
    }

    @Test
    public void playerFound() {
        Player p = stats.search("Lemieux");
        assertEquals("PIT", p.getTeam());
        assertEquals(45, p.getGoals());
        assertEquals(54, p.getAssists());
        assertEquals(45 + 54, p.getPoints());

    }
    
    @Test
    public void teamMembersFound(){
        List<Player> players = stats.team("EDM");
        assertEquals(3, players.size());
        for (Player player : players) {
            assertEquals("EDM", player.getTeam());
        }
    }
    
    @Test
    public void topScorersFound(){
        List<Player> players = stats.topScorers(2);
        assertEquals(3, players.size());
        assertEquals("Gretzky", players.get(0).getName());
        assertEquals("Lemieux", players.get(1).getName());
    }
}
