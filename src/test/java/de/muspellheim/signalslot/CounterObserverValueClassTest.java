/*
 * Copyright (c) 2013, Falko Schumann <www.muspellheim.de>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;

/**
 * Ported Qt simple example of signals and slots to Java observer pattern. This variant
 * use a special class for holding the value and observer logic.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public class CounterObserverValueClassTest {

    @Test
    public void testCounter() {
        Counter a = new Counter();
        Counter b = new Counter();
        a.value.addObserver(b.value);

        a.value().set(12);
        assertEquals(12, a.value().get());
        assertEquals(12, b.value().get());

        b.value().set(48);
        assertEquals(12, a.value().get());
        assertEquals(48, b.value().get());
    }

    public static class Counter {

        private Value value = new Value();

        public Value value() {
            return value;
        }

    }

    public static class Value<T> extends Observable implements Observer {

        private T value;

        public T get() {
            return value;
        }

        public void set(T value) {
            if ((this.value == null) || !this.equals(value)) {
                this.value = value;
                setChanged();
                notifyObservers(value);
            }
        }

        @Override
        public void update(Observable o, Object arg) {
            value = (T) arg;
        }

    }

}