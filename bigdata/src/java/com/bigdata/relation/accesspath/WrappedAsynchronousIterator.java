/*

Copyright (C) SYSTAP, LLC 2006-2008.  All rights reserved.

Contact:
     SYSTAP, LLC
     4501 Tower Road
     Greensboro, NC 27410
     licenses@bigdata.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
/*
 * Created on Oct 28, 2008
 */

package com.bigdata.relation.accesspath;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import com.bigdata.striterator.IChunkedIterator;
import com.bigdata.striterator.ICloseableIterator;

/**
 * An {@link IAsynchronousIterator} that wraps an {@link IChunkedIterator} or a
 * {@link ICloseableIterator}.
 * 
 * @param E
 *            The generic type of the visited chunks.
 * @param F
 *            The generic type of the source elements.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id: ThickAsynchronousIterator.java 2265 2009-10-26 12:51:06Z
 *          thompsonbry $
 */
public class WrappedAsynchronousIterator<E,F> implements IAsynchronousIterator<E> {

    private transient boolean open = true;

    private final IChunkedIterator<F> src;
    
    /**
     * 
     * @param src
     *            The source.
     * 
     * @throws IllegalArgumentException
     *             if <i>src</i> is <code>null</code>.
     */
    public WrappedAsynchronousIterator(final IChunkedIterator<F> src) {

        if (src == null)
            throw new IllegalArgumentException();
        
        this.src = src;
        
    }

//    private final void assertOpen() {
//        
//        if (!open)
//            throw new IllegalStateException();
//        
//    }

    public boolean hasNext() {

        return open && src.hasNext();

    }

    @SuppressWarnings("unchecked")
    public E next() {
        
        if (!hasNext())
            throw new NoSuchElementException();

        return (E) src.nextChunk();

    }

    public void remove() {

        src.remove();
        
    }

    /*
     * ICloseableIterator.
     */

    public void close() {

        if (open) {

            open = false;
            
//            if (src instanceof ICloseableIterator<?>) {
            
            ((ICloseableIterator<?>) src).close();
            
//            }
        
        }
        
    }

    /*
     * IAsynchronousIterator.
     */
    
    public boolean isExhausted() {

        return !hasNext();
        
    }

    /**
     * Delegates to {@link #hasNext()} since all data are local and timeouts can
     * not occur.
     */
    public boolean hasNext(long timeout, TimeUnit unit) {

        return hasNext();
        
    }

    /**
     * Delegates to {@link #next()} since all data are local and timeouts can
     * not occur.
     */
    public E next(long timeout, TimeUnit unit) {

        return next();
        
    }

}
