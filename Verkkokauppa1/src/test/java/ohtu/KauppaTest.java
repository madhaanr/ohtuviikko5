package ohtu;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import ohtu.verkkokauppa.Kauppa;
import ohtu.verkkokauppa.PankkiInterface;
import ohtu.verkkokauppa.Tuote;
import ohtu.verkkokauppa.VarastoInterface;
import ohtu.verkkokauppa.ViitegeneraattoriInterface;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author mhaanran
 */
public class KauppaTest {

    public KauppaTest() {
    }
 
    @Test
    public void ostetaanYksiTuote() {
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
    public void ostetaanKaksiTuotetta() {
        PankkiInterface pankki = mock(PankkiInterface.class);
       
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "perunoita", 15));
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("Marko", "0575");
        verify(pankki).tilisiirto("Marko", 1, "0575", "33333-44455", 20);
    }
    @Test
    public void ostetaanKaksiSamaaTuotetta() {
        PankkiInterface pankki = mock(PankkiInterface.class);
       
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "Bisse", 1));
        
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("Marko", "0575");
        verify(pankki).tilisiirto("Marko", 1, "0575", "33333-44455", 2);
    }
    @Test
    public void ostetaanYksiJuttuJaYritetaanOstaaToinenJokaOnLoppu() {
        PankkiInterface pankki = mock(PankkiInterface.class);
       
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "Bisse", 1));
        when(varasto.saldo(2)).thenReturn(0); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "Peruna", 1));
        
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("Marko", "0575");
        verify(pankki).tilisiirto("Marko", 1, "0575", "33333-44455", 1);
    }
    @Test
    public void kaksiEriAsiointia() {
        PankkiInterface pankki = mock(PankkiInterface.class);
       
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "Bisse", 1));
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "Peruna", 7));
        
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("Marko", "0575");
        verify(pankki).tilisiirto("Marko", 1, "0575", "33333-44455", 7);
    }
     @Test
    public void viitteetToimivatOikein() {
        PankkiInterface pankki = mock(PankkiInterface.class);
       
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "Bisse", 1));
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "Peruna", 7));
        
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1)
                .thenReturn(2);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("Marko", "0575");
        pankki.tilisiirto("Marko", 1, "0575", "33333-44455", 1);
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("Pekka", "12345");
        verify(pankki).tilisiirto("Pekka", 2, "12345", "33333-44455", 7);
    }
    @Test
    public void lisataanKoriinKaksiTuotettaJaPoistetaanKoristaTuoteEnnenOstamista() {
        PankkiInterface pankki = mock(PankkiInterface.class);
       
        VarastoInterface varasto = mock(VarastoInterface.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "Bisse", 1));
        when(varasto.saldo(2)).thenReturn(10); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "Porkkana", 5));
        
        ViitegeneraattoriInterface viite = mock(ViitegeneraattoriInterface.class);
        when(viite.uusi()).thenReturn(1);
        
        Kauppa kauppa = new Kauppa(varasto,pankki,viite);
            
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.poistaKorista(2);
        kauppa.tilimaksu("Marko", "0575");
        verify(pankki).tilisiirto("Marko", 1, "0575", "33333-44455",1);
    }
}
