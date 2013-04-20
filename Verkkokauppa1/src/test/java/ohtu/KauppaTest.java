package ohtu;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import ohtu.verkkokauppa.Kauppa;
import ohtu.verkkokauppa.OstoskoriInterface;
import ohtu.verkkokauppa.Pankki;
import ohtu.verkkokauppa.PankkiInterface;
import ohtu.verkkokauppa.Tuote;
import ohtu.verkkokauppa.VarastoInterface;
import ohtu.verkkokauppa.ViitegeneraattoriInterface;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author mhaanran
 */
public class KauppaTest {

    public KauppaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void markoOstaaMaitoa() {
        PankkiInterface pankki = mock(PankkiInterface.class);
        
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("Marko", "0575");
        verify(pankki).tilisiirto("Marko", 1, "0575", "33333-44455", 5);
    }
    @Test
    public void pekkaOstaMaitoa() {
        PankkiInterface pankki = mock(PankkiInterface.class);
        when(pankki.tilisiirto("pekka", 1, "12345", "33333-44455", 5)).thenReturn(Boolean.TRUE);
        
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto("pekka", 1, "12345", "33333-44455", 5);
    }
}
