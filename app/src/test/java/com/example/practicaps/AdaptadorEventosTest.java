package com.example.practicaps;


import com.example.practicaps.adaptadores.AdaptadorEventos;
import com.example.practicaps.utils.EventInfo;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdaptadorEventosTest {

    private AdaptadorEventos adaptador;

    @Before
    public void setUp() {
        adaptador = new AdaptadorEventos(null);
    }

    @Test
    public void addEventoTest() {
        // Check if addEvento method increases item count
        int itemCount = adaptador.getItemCount();
        adaptador.addEvento(new EventInfo("Event 1", 10, 30));
        assertEquals(itemCount + 1, adaptador.getItemCount());

        // Add Event 2
        itemCount = adaptador.getItemCount();
        adaptador.addEvento(new EventInfo("Event 2", 14, 0));
        assertEquals(itemCount + 1, adaptador.getItemCount());
    }

    @Test
    public void getItemCountTest() {
        // Check if getItemCount() returns correct count
        adaptador.addEvento(new EventInfo("Event 1", 10, 30));
        adaptador.addEvento(new EventInfo("Event 2", 14, 0));

        assertEquals(2, adaptador.getItemCount());
    }
}
