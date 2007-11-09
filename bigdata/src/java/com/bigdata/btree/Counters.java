/**

Copyright (C) SYSTAP, LLC 2006-2007.  All rights reserved.

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
package com.bigdata.btree;

/**
 * A helper class that collects statistics on an {@link AbstractBTree}.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 * 
 * @todo add nano timers and track storage used by the index. The goal is to
 *       know how much of the time of the server is consumed by the index, what
 *       percentage of the store is dedicated to the index, how expensive it is
 *       to do some scan-based operations (merge down, delete of transactional
 *       isolated persistent index), and evaluate the buffer strategy by
 *       comparing accesses with IOs.
 * 
 * @todo expose more of the counters using property access methods.
 */
public class Counters {

    protected final AbstractBTree btree;
    
    public Counters(AbstractBTree btree) {
        
        assert btree != null;
        
        this.btree = btree;
        
    }
    
    int nfinds = 0; // #of keys looked up in the tree by lookup(key).
    int nbloomRejects = 0; // #of keys rejected by the bloom filter in lookup(key).
    int ninserts = 0;
    int nremoves = 0;
    int nindexOf = 0;
    int ngetKey = 0;
    int ngetValue = 0;
    int rootsSplit = 0;
    int rootsJoined = 0;
    int nodesSplit = 0;
    int nodesJoined = 0;
    int leavesSplit = 0;
    int leavesJoined = 0; // @todo also merge vs redistribution of keys on remove (and insert if b*-tree)
    int nodesCopyOnWrite = 0;
    int leavesCopyOnWrite = 0;
    int nodesRead = 0;
    int leavesRead = 0;
    int nodesWritten = 0;
    int leavesWritten = 0;
    long bytesRead = 0L;
    long bytesWritten = 0L;
    
    /**
     * The #of nodes written on the backing store.
     */
    final public int getNodesWritten() {
        
        return nodesWritten;
        
    }
    
    /**
     * The #of leaves written on the backing store.
     */
    final public int getLeavesWritten() {
        
        return leavesWritten;
        
    }
    
    /**
     * The number of bytes read from the backing store.
     */
    final public long getBytesRead() {
        
        return bytesRead;
        
    }
    
    /**
     * The number of bytes written onto the backing store.
     */
    final public long getBytesWritten() {
        
        return bytesWritten;
        
    }

    // @todo consider changing to logging so that the format will be nicer
    // or just improve the formatting.
    public String toString() {
        
        return 
        "height="+btree.getHeight()+
        ", #nodes="+btree.getNodeCount()+
        ", #leaves="+btree.getLeafCount()+
        ", #entries="+btree.getEntryCount()+
        ", #find="+nfinds+
        ", #bloomRejects="+nbloomRejects+
        ", #insert="+ninserts+
        ", #remove="+nremoves+
        ", #indexOf="+nindexOf+
        ", #getKey="+ngetKey+
        ", #getValue="+ngetValue+
        ", #roots split="+rootsSplit+
        ", #roots joined="+rootsJoined+
        ", #nodes split="+nodesSplit+
        ", #nodes joined="+nodesJoined+
        ", #leaves split="+leavesSplit+
        ", #leaves joined="+leavesJoined+
        ", #nodes copyOnWrite="+nodesCopyOnWrite+
        ", #leaves copyOnWrite="+leavesCopyOnWrite+
        ", read ("+nodesRead+" nodes, "+leavesRead+" leaves, "+bytesRead+" bytes)"+
        ", wrote ("+nodesWritten+" nodes, "+leavesWritten+" leaves, "+bytesWritten+" bytes)"
        ;
        
    }

}
