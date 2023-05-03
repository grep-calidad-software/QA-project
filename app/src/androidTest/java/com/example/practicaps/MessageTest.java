package com.example.practicaps;

import static org.junit.Assert.assertEquals;

import com.example.practicaps.adaptadores.AdaptadorMensajes;
import com.example.practicaps.utils.Mensajes.MensajeRecibir;

import org.junit.Before;
import org.junit.Test;

public class MessageTest {
    private AdaptadorMensajes adaptador;

    @Before
    public void setUp() {
        adaptador = new AdaptadorMensajes(null);
    }

    @Test
    // Checks if the adapter if empty at start
    public void emptyListAtStart() {
        int itemCount = adaptador.getItemCount();
        assertEquals(itemCount, 0);
    }

    @Test
    // Checks if the adapter item count goes up when storing a new message
    public void getMessageCountTest() {
        int itemCount = adaptador.getItemCount();
        adaptador.addMensaje(new MensajeRecibir("test_name", "test_content", "1", System.currentTimeMillis()));
        assertEquals(itemCount + 1, adaptador.getItemCount());
    }
}
