/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.gregus.dao;

import java.io.Serializable;

/**
 * Interfejs obsługi klucza głównego w encji.
 * 
 * @author Grzegorz Guściora
 * @version 1.0
 * @param <PK>
 * @created 2014-03-03 09:00:00
 */
public interface Identifiable<PK extends Long> extends Serializable {

    /**
     * Pobieranie klucza głównego.
     * @return klucz główny.
     */
    PK getId();

    /**
     * Ustawienie klucza głównego.
     */
    void setId(PK id);

    /**
     * Sprawdzanie czy klucz główny jest ustawiony.
     * @return true jeżeli klucz główny jest ustawiony, false w przeciwnym przypadku.
     */
    boolean isIdSet();
}