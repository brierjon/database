/**

Copyright (C) SYSTAP, LLC 2006-2010.  All rights reserved.

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
 * Created on Sep 4, 2010
 */

package com.bigdata.bop.solutions;

import java.util.Map;

import com.bigdata.bop.BOp;
import com.bigdata.bop.PipelineOp;

/**
 * Base class for operators which sort binding sets.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 * 
 * @todo Define a distributed (external) merge sort operator.
 */
abstract public class SortOp extends PipelineOp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public interface Annotations extends PipelineOp.Annotations {

        /**
         * The {@link ComparatorOp} which will impose the ordering on the
         * binding sets.
         * 
         * @see ComparatorOp
         */
        String COMPARATOR = MemorySortOp.class.getName() + ".comparator";

    }

    /**
     * @param op
     */
    public SortOp(final SortOp op) {
        super(op);
    }

    /**
     * @param args
     * @param annotations
     */
    public SortOp(final BOp[] args, final Map<String, Object> annotations) {
        super(args, annotations);
    }

    /**
     * @see Annotations#COMPARATOR
     */
    public ComparatorOp getComparator() {
   
        return (ComparatorOp) getRequiredProperty(Annotations.COMPARATOR);
    
    }
    
}
