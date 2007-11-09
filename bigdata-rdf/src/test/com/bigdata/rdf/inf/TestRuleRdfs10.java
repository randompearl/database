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
/*
 * Created on Apr 13, 2007
 */

package com.bigdata.rdf.inf;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import com.bigdata.rdf.rio.IStatementBuffer;
import com.bigdata.rdf.rio.StatementBuffer;
import com.bigdata.rdf.store.AbstractTripleStore;

/**
 * Note: rdfs 6, 8, 10, 12, and 13 use the same base clase.
 * 
 * @see RuleRdfs06
 * @see RuleRdfs08
 * @see RuleRdfs10
 * @see RuleRdfs12
 * @see RuleRdfs13
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 */
public class TestRuleRdfs10 extends AbstractRuleTestCase {

    /**
     * 
     */
    public TestRuleRdfs10() {
    }

    /**
     * @param name
     */
    public TestRuleRdfs10(String name) {
        super(name);
    }

    /**
     * Test of {@link RuleRdfs10} where the data satisifies the rule exactly
     * once.
     * 
     * <pre>
     * (?u,rdfs:subClassOf,?u) :- (?u,rdf:type,rdfs:Class). 
     * </pre>
     */
    public void test_rdfs10_01() {

        AbstractTripleStore store = getStore();

        try {

            URI U = new URIImpl("http://www.foo.org/U");

            IStatementBuffer buffer = new StatementBuffer(store,
                    100/* capacity */);
            
            buffer.add(U, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS);

            // write on the store.
            buffer.flush();

            // verify statement(s).
            assertTrue(store.hasStatement(U, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS));
            assertEquals(1,store.getStatementCount());

            InferenceEngine inf = new InferenceEngine(store);

            // apply the rule.
            RuleStats stats = applyRule(inf,inf.rdfs10, 1/*expectedComputed*/);

            /*
             * validate the state of the primary store.
             */

            // told
            assertTrue(store.hasStatement(U, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS));
            
            // entailed
            assertTrue(store.hasStatement(U, URIImpl.RDFS_SUBCLASSOF, U));

            // final #of statements in the store.
            assertEquals(2,store.getStatementCount());

        } finally {

            store.closeAndDelete();

        }
        
    }
        
    /**
     * Test of {@link RuleRdfs10} where the data satisifies the rule exactly
     * twice.
     * 
     * <pre>
     * (?u,rdfs:subClassOf,?u) :- (?u,rdf:type,rdfs:Class). 
     * </pre>
     */
    public void test_rdfs10_02() {

        AbstractTripleStore store = getStore();

        try {

            URI U1 = new URIImpl("http://www.foo.org/U1");
            URI U2 = new URIImpl("http://www.foo.org/U2");

            IStatementBuffer buffer = new StatementBuffer(store,
                    100/* capacity */);
            
            buffer.add(U1, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS);
            buffer.add(U2, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS);

            // write on the store.
            buffer.flush();

            // verify statement(s).
            assertTrue(store.hasStatement(U1, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS));
            assertTrue(store.hasStatement(U1, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS));
            assertEquals(2,store.getStatementCount());

            InferenceEngine inf = new InferenceEngine(store);

            // apply the rule.
            RuleStats stats = applyRule(inf,inf.rdfs10, 2/*expectedComputed*/);

            /*
             * validate the state of the primary store.
             */

            // told
            assertTrue(store.hasStatement(U1, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS));
            assertTrue(store.hasStatement(U2, URIImpl.RDF_TYPE, URIImpl.RDFS_CLASS));
            
            // entailed
            assertTrue(store.hasStatement(U1, URIImpl.RDFS_SUBCLASSOF, U1));
            assertTrue(store.hasStatement(U2, URIImpl.RDFS_SUBCLASSOF, U2));

            // final #of statements in the store.
            assertEquals(4,store.getStatementCount());

        } finally {

            store.closeAndDelete();

        }
        
    }
        
}
