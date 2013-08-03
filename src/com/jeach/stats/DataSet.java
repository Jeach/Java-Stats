/*
 * DataSet.java - Created on Tue Feb  8 01:51:53 EST 2005
 *
 * Copyright (C) 2005 by Christian Jean.
 * All rights reserved.
 *
 * CONFIDENTIAL AND PROPRIETARY INFORMATION!
 *
 * Disclosure or use in part or in whole without prior written consent
 * constitutes an infringement of copyright laws which may be punishable
 * by law.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES
 * INCLUDING,  BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND  FITNESS FOR A PARTICULAR  PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * THE AUTHOR(S), COPYRIGHT  HOLDER(S)  OR ITS  CONTRIBUTOR(S) BE LIABLE FOR
 * ANY DIRECT,  INDIRECT,  INCIDENTAL, SPECIAL, EXEMPLARY,  OR CONSEQUENTIAL
 * DAMAGES  (INCLUDING, BUT NOT  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES;  LOSS OF USE, DATA, OR  PROFITS;  OR BUSINESS  INTERRUPTION)
 * HOWEVER  CAUSED  AND  ON  ANY  THEORY  OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT  LIABILITY, OR TORT (INCLUDING  NEGLIGENCE  OR OTHERWISE)  ARISING
 * IN  ANY WAY  OUT  OF THE  USE OF  THIS SOFTWARE,  EVEN IF  ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.jeach.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.log4j.Logger;

import com.jeach.tools.JxMath;
import com.jeach.tools.JxRand;
import com.jeach.tools.ToString;
import com.jeach.tools.counting.CountingList;

/**
 * The <code>DataSet</code> class is a container which is populated with data
 * values (samples). Once populated with data, it can then be used by the
 * <tt>Statistics</tt> instance to get a desired statistical computation.
 * <p>
 * Internally a data set contains a list of <code>Value</code> instances. When
 * objects which implement the <code>Number</code> interface are added to a
 * data-set, or native data types are added to a data-set, they are wrapped into
 * a new <code>Value</coe> instance before being appended to the list.
 * <p>
 * TODO: Test the shallow and deep copies!
 * TODO: Support the addition of a correlator for specific corrlated data types!
 * 
 * @author Christian Jean (java-stats@jeach.com)
 * @since DNAS-041125-000, Tue Feb 8 01:52:51 EST 2005
 */
public class DataSet implements Comparable {

        /** Log4J library */
        private static final Logger log = Logger.getLogger(DataSet.class);

        private static final String CORRELATE_PREFIX = "ds.";
        public static final String CORRELATE_FREQUENCY = CORRELATE_PREFIX
                        + "frequency";

        private List values = null;
        // private String correlation = null;

        private boolean isSorted = false;
        private boolean isReversed = false;
        private boolean isDirty = false;
        private boolean isModified = false; // add/remove operations

        // ///////////////////////////////////////////////////////////////////////
        // Constructors
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Constructs a default data set with no values.
         */
        public DataSet() {
                super();
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * data set.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(DataSet values) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * data set array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(DataSet values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * collection.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(Collection values) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array of Value instances.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(Value values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array of Number instances.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(Number values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(double values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(float values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(long values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(int values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(short values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(char values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with the values of the specified
         * array.
         * 
         * @param values
         *            to be inserted into this data set
         */
        public DataSet(byte values[]) {
                addValues(values);
        }

        /**
         * Constructs a data set and populates it with one value instance.
         * 
         * @param value
         *            the value to be inserted into this data set
         */
        public DataSet(Value value) {
                addValue(value);
        }

        // ///////////////////////////////////////////////////////////////////////
        // Add/Remove Functionality
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Adds a new data set to the existing data set.
         * 
         * @param values
         *            is a the data set to be inserted
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Fri Feb 18 11:16:42 EST 2005
         */
        public void addValues(DataSet values) {
                log.debug("Adding a DataSet instance!");

                if (values != null) {
                        addValues(values.getValues());
                }
        }

        /**
         * Adds an array of data sets to this instance.
         * 
         * @param values
         *            is an array of data set objects
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Fri Feb 18 11:16:42 EST 2005
         */
        public void addValues(DataSet values[]) {
                log.debug("Adding a DataSet array!");

                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValues(values[i]);
                        }
                }
        }

        /**
         * Adds the collection of new <code>Number</code>, <code>Value</code> or
         * <code>DataSet</code> objects. Any objects which are not of these types
         * will be silently ommited.
         * 
         * @param values
         *            is a collection of <code>Number</code>, <code>Value</code>, or
         *            <code>DataSet</code> objects.
         * @return a count of objects which were not added
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public int addValues(Collection values) {
                Iterator it = null;
                int rejected = 0;

                log.debug("Adding a collection of values!");

                if (values != null) {
                        it = values.iterator();
                }

                if (it != null) {
                        Object o = null;

                        while (it.hasNext()) {
                                o = it.next();

                                if (o == null) {
                                        continue;
                                }

                                if (o instanceof Value) {
                                        addValue((Value) o);
                                } else if (o instanceof Number) {
                                        addValue((Number) o);
                                } else if (o instanceof DataSet) {
                                        addValues((DataSet) o);
                                } else {
                                        log.debug("Rejected value of type:" + o.getClass());
                                        rejected++;
                                }
                        }

                        log.debug("Rejected " + rejected + " value instances!");
                }

                return rejected;
        }

        /**
         * Add a new <code>Object</code> to the existing data set.
         * <p>
         * It is expected that the instance provided will be one of <tt>Number</tt>
         * or </tt>Value</tt>.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(Object value) {
                if (value instanceof Number) {
                        addValue((Number) value);
                } else if (value instanceof Value) {
                        addValue((Value) value);
                }
        }

        /**
         * Add a new <code>Number</code> to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(Number value) {
                add(new Value(value), value);
        }

        /**
         * Adds an array of new <code>Number</code>'s to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(Number values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds a new variable to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(double value) {
                add(new Value(value));
        }

        /**
         * Adds an array of new variables to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(double values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds a new variable to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(float value) {
                add(new Value(value));
        }

        /**
         * Adds an array of new variables to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(float values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds a new variable to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(long value) {
                add(new Value(value));
        }

        /**
         * Adds an array of new variables to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(long values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds a new variable to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(int value) {
                add(new Value(value));
        }

        /**
         * Adds an array of new variables to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(int values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds a new variable to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(short value) {
                add(new Value(value));
        }

        /**
         * Adds an array of new variables to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(short values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds a new variable to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(char value) {
                add(new Value(value));
        }

        /**
         * Adds an array of new variables to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(char values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds a new variable to the existing data set.
         * 
         * @param value
         *            is a new variable to add to the set.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValue(byte value) {
                add(new Value(value));
        }

        /**
         * Adds an array of new variables to the existing data set.
         * 
         * @param values
         *            is an array of new variables.
         * @author Christian Jean (dev@jeach.com)
         * @since DNAS-041125-000, Wed Dec 22 08:49:16 EST 2004
         */
        public void addValues(byte values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        /**
         * Adds the specified value to this data set.
         * 
         * @param value
         */
        public void addValue(Value value) {
                if (value != null) {
                        add(value);
                }
        }

        /**
         * Adds the specified values to this data set.
         * 
         * @param values
         *            an array of <tt>Value</tt> instances
         */
        public void addValue(Value values[]) {
                if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                                addValue(values[i]);
                        }
                }
        }

        // ///////////////////////////////////////////////////////////////////////
        // Internal add/remove management
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Will set the appropriate flags if an operation occures which modifies the
         * internal content of this data set.
         * <p>
         * Also conditional of the <tt>Value</tt> instance.
         */
        private void modified(Value v) {
                if (v != null) {
                        modified();
                }
        }

        /**
         * Will set the appropriate flags if an operation occures which modifies the
         * internal content of this data set.
         * <p>
         * Also conditional of the <tt>DataSet</tt> instance.
         */
        private void modified(DataSet ds) {
                if (ds != null) {
                        modified();
                }
        }

        /**
         * Will set the appropriate flags if an operation occures which modifies the
         * internal content of this data set.
         * <p>
         * Also conditional of the boolean value.
         */
        private void modified(boolean b) {
                if (b) {
                        modified();
                }
        }

        /**
         * Will set the appropriate flags if an operation occurs which modifies the
         * internal content of this data set.
         */
        private synchronized void modified() {
                isModified = true;

                if (isSorted) {
                        isDirty = true;
                }

                log.debug("Setting modified=" + isModified + ", dirty=" + isDirty
                                + " (sorted=" + isSorted + ")");
        }

        // ///////////////////////////////////////////////////////////////////////
        // Remove functionality
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Removes all values contained with this data set.
         * <p>
         * This method is identical to calling <tt>empty()</tt>
         */
        public synchronized void removeAllValues() {
                log.debug("Removing all values!");
                empty();
        }

        public synchronized Value removeFirstValue() {
                Value v = null;

                if (values != null) {
                        doMaintenance();
                        v = (Value) values.remove(0);

                        modified(v);
                }

                log.debug("Removed first: " + v);

                return v;
        }

        public synchronized Value removeLastValue() {
                Value v = null;
                int count = 0;

                if (values != null) {
                        doMaintenance();
                        count = values.size();
                        v = (Value) values.remove(count - 1);

                        modified(v);
                }

                log.debug("Removed last: " + v);

                return v;
        }

        /**
         * Removes the instance of <tt>Value</tt> which is located at the specified
         * index.
         * 
         * @param index
         *            location to be removed
         * @return the <tt>Value</tt> instance that was removed, <tt>null</tt> if
         *         none was removed
         */
        public synchronized Value removeValueAt(int index) {
                Value v = null;

                log.debug("Remove value at: " + index);

                if (values != null) {
                        if (index >= 0 && index < values.size()) {
                                doMaintenance();
                                v = (Value) values.remove(index);
                        }

                        modified(v);
                }

                log.debug("-> Removed: " + v);

                return v;
        }

        /**
         * Attempts to remove a <tt>Value</tt> instance from the data set. Since
         * <tt>Value</tt> instances are immutable (in value), only the specified
         * instance will be removed. An instance with the same numerical value will
         * not be removed.
         * <p>
         * For example, given the following values:
         * <p>
         * <tt>Value v1 = new Value(1);</tt> <br>
         * <tt>Value v2 = new Value(1);</tt> <br>
         * <p>
         * And a dataset with one sample:
         * <p>
         * <tt>DataSet ds = new DataSet(v1);</tt>
         * <p>
         * Trying to remove another instance with the same value will not work
         * <p>
         * <tt>boolean b = ds.containsValue(v2);  // returns false</tt>
         * <p>
         * Our boolean value 'b' will be <tt>false</tt>
         * <p>
         * To remove any value regardless of its instance, use the other
         * <tt>removeValue()</tt> and <tt>removeValues()</tt> methods accepting
         * primitives as parameter.
         * 
         * @param value
         *            instance to be removed
         * @return <tt>true</tt> if value was removed, <tt>false</tt> otherwise
         */
        public synchronized boolean removeValue(Value value) {
                boolean b = false;

                log.debug("Removing value: " + value);

                if (value != null && values != null) {
                        doMaintenance();
                        b = values.remove(value);

                        modified(b);
                }

                log.debug("-> Removed: " + b);

                return b;
        }

        /**
         * Removes the first instance of <tt>Value</tt> having the the specified
         * numerical value.
         * 
         * @param value
         *            to be searched
         * @return the <tt>Value</tt> instance that was removed, <tt>null</tt> if
         *         none was removed
         */
        public synchronized Value removeValue(long value) {
                return removeValue((double) value);
        }

        /**
         * Removes the first instance of <tt>Value</tt> having the the specified
         * numerical value.
         * 
         * @param value
         *            to be searched
         * @return the <tt>Value</tt> instance that was removed, <tt>null</tt> if
         *         none was removed
         */
        public synchronized Value removeValue(double value) {
                Iterator it = null;
                Value v = null;

                log.debug("Removing value: " + value);

                it = getIterator(); // This will doMaintenance()

                while (it.hasNext()) {
                        v = (Value) it.next();

                        if (v == null)
                                continue;

                        if (v.doubleValue() == value) {
                                it.remove();
                                break;
                        }

                        v = null;
                }

                modified(v);

                log.debug("-> Removed: " + v);

                return v;
        }

        /**
         * Removes all instances of <tt>Value</tt> having the the specified
         * numerical value.
         * 
         * @param value
         *            to be searched
         * @return a <tt>DataSet</tt> instance containing all values which were
         *         removed, <tt>null</tt> if none were removed
         */
        public DataSet removeValues(long value) {
                return removeValues((double) value);
        }

        /**
         * Removes all instances of <tt>Value</tt> having the the specified
         * numerical value.
         * 
         * @param value
         *            to be searched
         * @return a <tt>DataSet</tt> instance containing all values which were
         *         removed, <tt>null</tt> if none were removed
         */
        public synchronized DataSet removeValues(double value) {
                Iterator it = null;
                Value v = null;
                DataSet ds = null;

                log.debug("Removing value: " + value);

                it = getIterator(); // This will doMaintenance()

                while (it.hasNext()) {
                        v = (Value) it.next();
                        if (v == null)
                                continue;

                        if (v.doubleValue() == value) {
                                if (ds == null)
                                        ds = new DataSet();
                                it.remove();
                                ds.addValue(v);
                        }
                }

                modified(ds);

                log.debug("-> Removed: " + ds);

                return ds;
        }

        // ///////////////////////////////////////////////////////////////////////
        // Contains Functionality
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Determin if the specified <tt>Value</tt> instance is contained within
         * this data set. Since <tt>Value</tt> instances are immutable (in value),
         * only the exact specified instance will be matched. An instance with the
         * same numerical value will not be found.
         * <p>
         * For example, given the following values:
         * <p>
         * <tt>Value v1 = new Value(16);</tt> <br>
         * <tt>Value v2 = new Value(16);</tt> <br>
         * <p>
         * And a dataset with one sample:
         * <p>
         * <tt>DataSet ds = new DataSet(v1);</tt>
         * <p>
         * Trying to find another instance with the same value will not work
         * <p>
         * <tt>boolean b = ds.containsValue(v2);  // returns false</tt>
         * <p>
         * Our boolean value 'b' will be <tt>false</tt>
         * <p>
         * To find any value regardless of instance, use the other
         * <tt>containsValue()</tt> method accepting primitives as parameters.
         * 
         * @param value
         *            instance to be searched
         * @return <tt>true</tt> if value was found, <tt>false</tt> otherwise
         */
        public synchronized boolean containsValue(Value value) {
                boolean b = false;

                log.debug("Contains value: " + value);

                if (value != null && values != null) {
                        doMaintenance();
                        b = values.contains(value);
                }

                log.debug("-> Found: " + b);

                return b;
        }

        /**
         * Requests the first instance of <tt>Value</tt> which has the specified
         * numerical value.
         * 
         * @param value
         *            to be searched
         * @return an instance of <tt>Value</tt> if one was found, <tt>null</tt>
         *         otherwise
         */
        public Value containsValue(long value) {
                return containsValue((double) value);
        }

        /**
         * Requests the first instance of <tt>Value</tt> which has the specified
         * numerical value.
         * 
         * @param value
         *            to be searched
         * @return an instance of <tt>Value</tt> if one was found, <tt>null</tt>
         *         otherwise
         */
        public synchronized Value containsValue(double value) {
                Iterator it = null;
                Value v = null;

                log.debug("Contains value: " + value);

                it = getIterator(); // This will doMaintenance()

                while (it.hasNext()) {
                        v = (Value) it.next();

                        if (v == null)
                                continue;

                        if (v.doubleValue() == value) {
                                break;
                        }

                        v = null;
                }

                log.debug("-> Found: " + v);

                return v;
        }

        /**
         * Requests all <tt>Value</tt> instances contained within this dataset
         * having the specified numerical value.
         * 
         * @param value
         *            to be searched
         * @return an instance of a <tt>DataSet</tt> if one or more values were
         *         found, <tt>null</tt> otherwise
         */
        public DataSet containsValues(long value) {
                return containsValues((double) value);
        }

        /**
         * Requests all <tt>Value</tt> instances contained within this dataset
         * having the specified numerical value.
         * 
         * @param value
         *            to be searched
         * @return an instance of a <tt>DataSet</tt> if one or more values were
         *         found, <tt>null</tt> otherwise
         */
        public synchronized DataSet containsValues(double value) {
                Iterator it = null;
                Value v = null;
                DataSet ds = null;

                log.debug("Contains values: " + value);

                it = getIterator(); // This will doMaintenance()

                while (it.hasNext()) {
                        v = (Value) it.next();

                        if (v == null)
                                continue;

                        if (v.doubleValue() == value) {
                                if (ds == null)
                                        ds = new DataSet();

                                ds.addValue(v);
                        }
                }

                log.debug("-> Found: " + ds);

                return ds;
        }

        // ///////////////////////////////////////////////////////////////////////
        // Ordering/Sorting Functionality
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Requests wether this data set is sorted in reverse order. Note that if
         * the data-set is not sorted the reversed state is of no consequence.
         * 
         * @return true if sorted in reverse order, false otherwise.
         */
        public synchronized boolean isReversed() {
                return isReversed;
        }

        /**
         * Will reverse the sort order of this data-set. This method does not toggle
         * the reverse state but rather implicitly sets it to a reverse state. In
         * other words, multiple invocations of this method has no effect.
         * <p>
         * This is the same as calling <tt>setReversed(true)</tt>.
         * 
         * @param reversed
         *            <tt>true</tt> if reverse order is desired, <tt>false</tt>
         *            otherwise.
         */
        public synchronized void setReversed() {
                setReversed(true);
        }

        /**
         * Will reverse the sort order for this data-set.
         * 
         * @param reversed
         *            <tt>true</tt> if reverse order is desired, <tt>false</tt>
         *            otherwise.
         */
        public synchronized void setReversed(boolean reversed) {
                if (isReversed != reversed) {
                        isReversed = reversed;
                        modified();
                }
        }

        /**
         * Requests to know if this data set is sorted.
         * 
         * @return true if it is, false otherwise
         */
        public synchronized boolean isSorted() {
                return isSorted;
        }

        public synchronized void setSorted() {
                setSorted(true);
        }

        /**
         * Sets this data-set in sorted or unsorted order. When sorted is requested,
         * the data set will remain sorted at all times.
         * <p>
         * It is important to note that once the sort mode has been enabled, the
         * initial order of the data-set will be preserved and will not be lost.
         * <p>
         * The internal 'reverse' state will be preserved when invoking this method
         * and is like invoking <tt>setSorted(boolean, isReversed())</tt>.
         * 
         * @param sorted
         *            <tt>true</tt> will sort the data-set, <tt>false</tt> will not
         *            sorted the data-set
         */
        public synchronized void setSorted(boolean sorted) {
                setSorted(sorted, isReversed);
        }

        /**
         * Sets this data-set in sorted or unsorted order. When sorted is requested,
         * the data set will remain sorted at all times.
         * <p>
         * It is important to note that once the sort mode has been enabled, the
         * initial order of the data-set will be preserved and will not be lost.
         * <p>
         * The internal 'reverse' state will be preserved when invoking this method
         * and is like invoking <tt>setSorted(boolean, isReversed())</tt>.
         * 
         * @param sorted
         *            <tt>true</tt> will sort the data-set, <tt>false</tt> will not
         *            sorted the data-set
         * @param reversed
         *            indicates that the sort order will be reversed
         */
        public synchronized void setSorted(boolean sorted, boolean reversed) {
                if (isSorted != sorted) {
                        isSorted = sorted;
                        isDirty = true;
                        isModified = true;
                }

                setReversed(reversed);
        }

        // ///////////////////////////////////////////////////////////////////////
        // Internal List Management
        // ///////////////////////////////////////////////////////////////////////
        /**
         * Internal method to add a new value to the list of values.
         * 
         * @param value
         *            to be added
         */
        private synchronized void add(Value value) {
                add(value, null);
        }

        /**
         * Internal method to add a new value to the list of values.
         * <p>
         * All setters should lead to this method.
         * 
         * @param value
         *            to be added
         */
        private synchronized void add(Value value, Object correlation) {
                log.debug("Adding value: " + value);

                if (value != null) {

                        if (correlation != null) {
                                log.debug("-> Correlation: " + correlation);
                                value.addCorrelation(correlation);
                        }

                        if (values == null) {
                                log.debug("-> Allocating space!");
                                values = new ArrayList();
                        }

                        values.add(value);

                        modified();
                }
        }

        // ///////////////////////////////////////////////////////////////////////
        // Internal Management
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Internal method to get an existing value from our data-set.
         * <p>
         * Specify an index outside of a valid range will simply ignore the request
         * and return a <tt>null</tt> value.
         * <p>
         * All accessors should lead to one of three methods (including this one).
         * 
         * @param index
         *            of the value to be retrieved
         * @return a value instance
         */
        private synchronized Value get(int index) {
                Value value = null;

                log.debug("Getting value at " + index);

                if (values != null) {
                        if (index >= 0 && index < values.size()) {
                                doMaintenance();
                                value = (Value) values.get(index);
                        }
                }

                log.debug("Returning value: " + value);

                return value;
        }

        /**
         * Internal method which will return a list iterator. If the list is null,
         * an empty iterator will be provided.
         * <p>
         * All accessors should lead to one of three methods (including this one).
         * 
         * @return a valid iterator
         */
        private synchronized Iterator getIterator() {
                doMaintenance();
                return values != null ? getValues().iterator() : Collections.EMPTY_LIST
                                .iterator();
        }

        /**
         * This method allows for sorting of our values if our dirty flag is set.
         * This method should be called from every low-level accessor.
         */
        private synchronized void doMaintenance() {
                if (isDirty && isSorted) {
                        if (!isReversed) {
                                log.debug("Sorting!");
                                Collections.sort(values);
                        } else {
                                log.debug("Sorting (reversed)!");
                                Collections.sort(values, Collections.reverseOrder());
                        }
                }
        }

        // ///////////////////////////////////////////////////////////////////////
        // Public Accessors
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Counts all of the values contained in this data set.
         * 
         * @return a count of values
         */
        public synchronized int getCount() {
                return values != null ? values.size() : 0;
        }

        /**
         * Retrieves the first value of this data set.
         * <p>
         * This is equivalent to calling <code>getValue()</code>
         * 
         * @return a value instance
         */
        public Value getFirstValue() {
                return get(0);
        }

        /**
         * Retrieves the last value of this data set.
         * 
         * @return a value instance
         */
        public Value getLastValue() {
                return get(getCount() - 1);
        }

        /**
         * Retrieves the collection of <tt>Value</tt> instances contained within
         * this data set.
         * <p>
         * All accessors should lead to one of three methods (including this one).
         * 
         * @return a collection of Value instances
         */
        public synchronized Collection getValues() {
                doMaintenance();
                return values != null ? Collections.unmodifiableList(values) : null;
        }

        /**
         * Retrieves a value from this data-set which is located at the specified
         * index.
         * 
         * @return a value instance
         */
        public Value getValueAt(int index) {
                return get(index);
        }

        /**
         * Retrieves a random value from this data-set. All values have an equal
         * chance of being selected.
         * 
         * @return a value instance
         */
        public Value getRandomValue() {
                Value v = null;
                int c = getCount();

                if (c > 0) {
                        v = get(JxRand.getInt(c - 1));
                }

                log.debug("Value: " + v);

                return v;
        }

        // ///////////////////////////////////////////////////////////////////////
        // Static Accessors
        // ///////////////////////////////////////////////////////////////////////

        /**
         * Convenient method which 'safely' converts a Value instance to its double
         * equivalent. If the instance is <tt>null</tt>, a value of <tt>0.0</tt>
         * will be returned.
         * 
         * @param value
         *            a <tt>Value</tt> instance
         * @return a double value
         */
        public static double getValue(Value value) {
                double v = 0.0;

                if (value != null) {
                        v = value.getValue();
                }

                return v;
        }

        /**
         * Tests if this data set contains any values.
         * 
         * @return <code>true</code> if no values are contain in this data-set,
         *         <code>false</code> otherwise.
         */
        public synchronized boolean isEmpty() {
                return values != null ? values.isEmpty() : true;
        }

        /**
         * Requests to clear and empty this data set. When this is called, it is
         * like instantiation a new data set with the default constructor.
         * <p>
         * If this data set is populated with values when this method is called, the
         * <tt>isModified()</tt> will <u>not</u> return <tt>true</tt>.
         * <p>
         * This method slightly differs from its <tt>empty()</tt> counter part.
         * 
         * @return a count of values which were removed from the data set
         */
        public synchronized int clear() {
                int count = getCount();

                if (values != null) {
                        values.clear();
                        values = null;
                }

                isSorted = false;
                isReversed = false;
                isModified = false;

                return count;
        }

        /**
         * Requests to empty this data set of all its <tt>Value</tt> instances. When
         * this is called, it is like instantiation a new data set with the default
         * constructor, but preserving the <tt>sort</tt> and <tt>reversed</tt>
         * states.
         * <p>
         * If this data set is populated with values when this method is called, the
         * <tt>isModified()</tt> <u>will</u> return <tt>true</tt>.
         * <p>
         * This method slightly differs from its <tt>clear()</tt> counter part.
         * 
         * @return a count of values which were removed from the data set
         */
        public synchronized int empty() {
                int count = getCount();

                if (values != null) {
                        values.clear();
                        values = null;
                }

                if (count > 0) {
                        isModified = true;
                }

                return count;
        }

        /**
         * Requests if the data set has been modified with additions and removals of
         * values or emptied of its content since a computation was made.
         * <p>
         * When an operation which modifies the internal content of the data set
         * occures, the <tt>modified</tt> flag is set and the internal caches (when
         * used) are flushed.
         * 
         * @return <tt>true</tt> if data set was modified, <tt>false</tt> otherwise
         */
        public synchronized boolean isModified() {
                return isModified;
        }

        ////////////////////////////////////////////////////////////////////////////
        // /
        // Statistics Section
        ////////////////////////////////////////////////////////////////////////////
        // /

        /**
         * A measure of location, or central tendency.
         * <p>
         * Calculates the average (arithmetic mean) of a data set. The outcome is
         * affected by outrageous values (small or large).
         * <p>
         * Operates on a <b>raw</b> or <b>sorted</b> data set.
         * 
         * @return a computation of the mean value.
         * @since 1.0
         */
        public synchronized Value getMean() {
                log.debug("Calculating mean!");

                Value sum = null;
                Value mean = null;
                int count = getCount();

                if (count > 0) {
                        sum = getSummation();
                }

                if (sum != null) {
                        mean = new Value(sum.doubleValue() / count);
                }

                log.info("Mean = " + mean);

                return mean;
        }

        /**
         * Sums all the values within this data set.
         * <p>
         * Operates on a <b>raw</b> or <b>sorted</b> data set.
         * 
         * @return a value object
         * @since 1.0
         */
        public synchronized Value getSummation() {
                log.debug("Calculating sum!");

                Value sum = null;
                double s = 0.0;

                if (!isEmpty()) {
                        Iterator it = getIterator();

                        while (it.hasNext()) {
                                Value v = (Value) it.next();

                                if (v != null) {
                                        s += v.getValue();
                                }
                        }

                        sum = new Value(s);
                }

                log.info("Summation = " + sum);

                return sum;
        }

        /**
         * A measure of location, or central tendency.
         * <p>
         * Calculates the middle value in a distribution, above and below which lie
         * an equal number of values. A median value is a calculated representation
         * of central distribution and is not affected by outrageous variables.
         * <p>
         * In the event the data set has an odd number of values, the mean will be
         * obtained by returning the middle value.
         * <p>
         * In the event the data set has an even number of values, the mean will be
         * returned by averaging the two middle values.
         * <p>
         * Operates on a <b>sorted</b> data set only.
         * <p>
         * Given an odd-count data set of <tt>1, 2, 3, 4, 5</tt>, the median value
         * would be the middle value of <tt>3</tt>.
         * <p>
         * Given an even-count data set of <tt>1, 2, 3, 4, 5, 6</tt>, the median
         * value would be <tt>3.5</tt> (<tt>3 + 4 = 7</tt>, then
         * <tt>7 / 2 = 3.5</tt>, we devide by two because we have two middle
         * values).
         * 
         * @return A computation of the median value.
         * @since 1.0
         */
        public synchronized Value getMedian() {
                log.debug("Calculating median!");

                DataSet ds = getSorted();
                Value median = null;

                int count = ds.getCount();

                if (count > 0) {
                        int index = count / 2;

                        double sum = getValue(ds.getValueAt(index));

                        if (JxMath.isEven(count)) {
                                sum = sum + getValue(ds.getValueAt(index - 1));
                                median = new Value(sum / 2);
                        } else {
                                median = new Value(sum);
                        }
                }

                log.info("Median = " + median);

                return median;
        }

        /**
         * A measure of location, or central tendency.
         * <p>
         * Calculates the midle range by finding the arithmetic mean of the maximum
         * and minimum values in a data set.
         * <p>
         * It is highly sensitive to the first and last values and ignores all but
         * two data points, therefore it is rarely used in statistical analysis.
         * <p>
         * For example: <tt>((min + max) / 2)</tt>
         * <p>
         * Operates on a <b>raw</b> or <b>sorted</b> data set.
         * <p>
         * TODO: May want to have an option to clip min/max values. For example,
         * specifying 2, would clip the two largest and two smallest values prior to
         * calculating the mid range.
         * 
         * @return A computation of the mid range
         */
        public synchronized Value getMidRange() {
                log.debug("Calculating mid-range!");

                Value mid = null;
                Value min = getMinimum();
                Value max = getMaximum();

                if (min != null && max != null) {
                        mid = new Value((min.doubleValue() + max.doubleValue()) / 2);
                }

                log.info("MidRange = " + mid);

                return mid;
        }

        /**
         * Retrieves the smallest value in this data set.
         * <p>
         * Operates on a <b>raw</b> or <b>sorted</b> data set.
         * 
         * @return a value instance
         */
        public synchronized Value getMinimum() {
                log.debug("Calculating minimum!");

                Value min = null;
                DataSet ds = getSorted();

                if (ds != null) {
                        if (ds.isReversed()) {
                                min = ds.getLastValue();
                        } else {
                                min = ds.getFirstValue();
                        }
                }

                log.info("Minimum = " + min);

                return min;
        }

        /**
         * Retrieves the largest value in this data set.
         * <p>
         * Operates on a <b>raw</b> or <b>sorted</b> data set.
         * 
         * @return a value instance
         */
        public synchronized Value getMaximum() {
                log.debug("Calculating maximum!");

                Value max = null;
                DataSet ds = getSorted();

                if (ds != null) {
                        if (ds.isReversed()) {
                                max = ds.getFirstValue();
                        } else {
                                max = ds.getLastValue();
                        }
                }

                log.info("Maximum = " + max);

                return max;
        }

        /**
         * The range is calculated by subtracting the smallest value from the
         * greatest. It provides an indication of statistical dispersion.
         * <p>
         * Operates on a <b>raw</b> or <b>sorted</b> data set.
         * 
         * @return a double value
         */
        public Value getRange() {
                log.debug("Calculating range!");

                Value min = getMinimum();
                Value max = getMaximum();
                Value range = null;

                if (min != null && max != null) {
                        range = new Value(max.doubleValue() - min.doubleValue());
                }

                log.info("Range = " + range);

                return range;
        }

        /**
         * Returns the mode, or most fequently occuring value.
         * <p>
         * With a data set containing the following values:
         * <p>
         * <tt>11, 22, 22, 33, 33, 44, 44, 44, 55, 55, 55</tt>
         * <p>
         * A <tt>Value</tt> instance would be returned having the value of 44
         * 
         * @return a <tt>DataSet</tt> instance
         */
        public Value getMode() {
                return p_getMode().getFirstValue();
        }

        /**
         * Returns the mode (most fequently occuring) values.
         * <p>
         * With a data set containing the following values:
         * <p>
         * <tt>11, 22, 22, 33, 33, 44, 44, 44, 55, 55, 55</tt>
         * <p>
         * Passing a <tt>false</tt> parameter, would return a data set containing:
         * <p>
         * <tt>
         * 44<sup>[frequency=3]</sup>,
         * 55<sup>[frequency=3]</sup>, 
         * 22<sup>[frequency=2]</sup>, 
         * 33<sup>[frequency=2]</sup>, 
         * 11<sup>[frequency=1]</sup>
    * </tt>
         * <p>
         * The data set will be ordered by frequency!
         * <p>
         * Passing a <tt>true</tt> parameter, would return a data set containing:
         * <p>
         * <tt>
         * 44<sup>[frequency=3]</sup>,
         * 55<sup>[frequency=3]</sup>
    * </tt>
         * <p>
         * Where the only values in the data set are 44 and 55. These values are
         * returned from all the others because they are both tied as being the most
         * frequently occuring.
         * <p>
         * We say that it is limited in the sense that only the values with the
         * highest frequencies are returned. Had another value been in the data set
         * a total of 3 times, it too would have been included in the new data set
         * also. Had the value of 44 occured only 1 or 2 times, only 55 would have
         * been returned.
         * <p>
         * Calling <tt>getMode(true)</tt> will differ from calling
         * <tt>getMode(1)</tt>. And calling <tt>getMode(false)</tt> will always
         * return a dataset with the same values as if <tt>getUnique()</tt> had been
         * called (but ordered differently).
         * 
         * @param limit
         *            if we should limit our set to only the highest frequencies
         * @return a <tt>DataSet</tt> instance
         */
        public DataSet getMode(boolean limit) {
                DataSet ds = p_getMode();
                Iterator it = ds.getIterator();
                Value v = null;
                double freq = 0;
                double last = 0;
                int count = 0;

                log.debug("Mode: " + ds);

                if (limit && !ds.isEmpty()) {
                        while (it.hasNext()) {
                                v = (Value) it.next();

                                freq = getFrequency(v).doubleValue();

                                if (count == 0 || freq == last) {
                                        count++;
                                        last = freq;
                                } else {
                                        break;
                                }
                        }
                }

                return limit ? ds.getFirst(count) : ds;
        }

        /**
         * Returns the mode (most fequently occuring) values.
         * <p>
         * With a data set containing the following values:
         * <p>
         * <tt>11, 22, 22, 33, 33, 44, 44, 44, 55, 55, 55</tt>
         * <p>
         * Calling <tt>getMode(1)</tt> would return a data set containing:
         * <p>
         * <tt>
    * 44<sup>[frequency=3]</sup> 
    * </tt>
         * <p>
         * Calling <tt>getMode(2)</tt> would return a data set containing:
         * <p>
         * <tt>
         * 44<sup>[frequency=3]</sup>, 
         * 55<sup>[frequency=3]</sup> 
    * </tt>
         * <p>
         * Calling <tt>getMode(3)</tt> would return a data set containing:
         * <p>
         * <tt>
         * 44<sup>[frequency=3]</sup>, 
         * 55<sup>[frequency=3]</sup>, 
         * 22<sup>[frequency=2]</sup> 
    * </tt>
         * <p>
         * And so on. The data set will be ordered based on frequency!
         * 
         * @param unique
         *            if we should limit it to the highest frequency
         * @return a <tt>DataSet</tt> instance
         */
        public DataSet getMode(int count) {
                DataSet ds = p_getMode();
                return ds.getFirst(count);
        }

        /**
         * Internal method which allows us to build a new data set containing
         * frequencies.
         * 
         * @return a <tt>DataSet</tt> instance
         */
        protected synchronized DataSet p_getMode() {
                List list = new CountingList();
                Iterator it = null;
                DataSet ds = new DataSet();

                log.debug("Getting mode!");

                it = getIterator();

                if (it != null) {
                        while (it.hasNext()) {
                                Value v = (Value) it.next();
                                log.debug("Adding value: " + v);
                                list.add(v.doubleValue());
                        }

                        Object values[] = ((CountingList) list).getValues();

                        log.debug("Values = " + values);
                        for (int i = 0; i < values.length; i++) {
                                log.debug("Value " + i + ", " + values[i] + ", "
                                                + values[i].getClass());
                                ds.addValue(values[i]);
                        }
                }

                return ds;
        }

        /**
         * Subtracts all the values within this data set.
         * 
         * @return a value object
         */
        public double getSubtraction() {
                double sub = 0.0;

                Iterator it = getIterator();

                while (it.hasNext()) {
                        Value v = (Value) it.next();

                        if (v != null) {
                                sub = sub - v.getValue();
                        }
                }

                return sub;
        }

        /**
         * Divides all the values within this data set.
         * <p>
         * TODO: Might have a bug when a zero is found in the middle of the data set
         * <p>
         * TODO: Not protected for division by zero!
         * 
         * @return a value object
         */
        public double getDivision() {
                double div = 0.0;
                boolean init = true;

                Iterator it = getIterator();

                while (it.hasNext()) {
                        Value v = (Value) it.next();

                        if (v != null) {
                                if (init) {
                                        div = v.getValue();
                                        init = false;
                                } else {
                                        div = div / v.getValue();
                                }
                        }
                }

                return div;
        }

        /**
         * Divides all the values within this data set.
         * <p>
         * TODO: Might have a bug when a zero is found in the middle of the data set
         * 
         * @return a value object
         */
        public double getMultiplication() {
                double mul = 0.0;
                boolean init = true;

                Iterator it = getIterator();

                while (it.hasNext()) {
                        Value v = (Value) it.next();

                        if (v != null) {
                                if (init) {
                                        mul = v.getValue();
                                        init = false;
                                } else {
                                        mul = mul * v.getValue();
                                }
                        }
                }

                return mul;
        }

        public Value getFrequency(Number value) {
                Value freq = null;

                if (value != null) {
                        freq = getFrequency(value.doubleValue());
                }

                return freq;
        }

        public Value getFrequency(short value) {
                return getFrequency((double) value);
        }

        public Value getFrequency(long value) {
                return getFrequency((double) value);
        }

        public Value getFrequency(int value) {
                return getFrequency((double) value);
        }

        public Value getFrequency(float value) {
                return getFrequency((double) value);
        }

        public Value getFrequency(double value) {
                Value count = null;
                int cnt = 0;

                if (!isEmpty()) {
                        Iterator it = getIterator();

                        while (it.hasNext()) {
                                Value v = (Value) it.next();

                                if (v != null) {
                                        if (v.doubleValue() == value) {
                                                cnt++;
                                        }
                                }
                        }

                        count = new Value(cnt);
                }

                log.debug("Frequency of " + value + " is " + count);

                return count;
        }

        /**
         * Creates a new dataset which consists of the distance between the each
         * observations and the mean value.
         * <p>
         * Example:
         * <p>
         * data1 - mean, data2 - mean, data3 - mean, ...
         * 
         * @return a dataset instance
         */
        public DataSet getDistance() {
                Value mean = getMean();
                return mean != null ? getDistance(mean.doubleValue()) : null;
        }

        /**
         * Creates a new dataset which consists of the distance between each
         * observation and the specified value.
         * <p>
         * Example:
         * <p>
         * data1 - value, data2 - value, data3 - value, ...
         * 
         * @return a dataset instance
         */
        public DataSet getDistance(double value) {
                DataSet ds = null;
                Iterator it = null;

                it = getIterator();

                if (it != null) {
                        ds = new DataSet();

                        while (it.hasNext()) {
                                Value v = (Value) it.next();

                                if (v != null) {
                                        ds.addValue(v.doubleValue() - value);
                                }
                        }
                }

                log.info("Distance = " + ds.printValues());

                return ds;
        }

        /**
         * Creates a new dataset which consists of the squared distance between each
         * observation and the mean value.
         * <p>
         * Example:
         * <p>
         * (data1 - mean)^2, (data2 - mean)^2, (dataN - mean)^2
         * 
         * @return a dataset instance
         */
        public DataSet getSquaredDistance() {
                Value mean = getMean();
                return mean != null ? getSquaredDistance(mean.doubleValue()) : null;
        }

        /**
         * Creates a new dataset which consists of the squared distance between each
         * observation and the mean value.
         * <p>
         * Example:
         * <p>
         * (data1 - value)^2, (data2 - value)^2, (dataN - value)^2
         * 
         * @return a dataset instance
         */
        public DataSet getSquaredDistance(double value) {
                DataSet ds = null;
                Iterator it = null;

                ds = getDistance(value);

                if (ds != null) {
                        it = ds.getIterator();
                }

                if (it != null) {
                        ds = new DataSet();

                        while (it.hasNext()) {
                                Value v = (Value) it.next();

                                if (v != null) {
                                        ds.addValue(Math.pow(v.doubleValue(), 2));
                                }
                        }
                }

                log.info("Squared Distance = " + ds.printValues());

                return ds;
        }

        /**
         * The variance is a measure of statistical dispersion, averaging the
         * squared distance of its possible values from the mean.
         * <p>
         * Whereas the mean is a way to describe the location of a distribution, the
         * variance is a way to capture its scale or degree of being spread out.
         * <p>
         * The positive square root of the variance, called the standard deviation,
         * has the same units as the original variable and can be easier to
         * interpret for this reason.
         * <p>
         * ((data1 - mean)^2 + (data2 - mean)^2 + (dataN - mean)^2) / count
         * 
         * @return
         */
        public Value getVariance() {
                DataSet ds = null;
                Value sum = null;
                Value var = null;
                double count = 0;

                ds = getSquaredDistance();

                if (ds != null) {
                        sum = ds.getSummation();
                        count = ds.getCount();
                }

                if (count > 0 && sum != null) {
                        var = new Value(sum.doubleValue() / count);
                }

                return var;
        }

        /**
         * The standard deviation measures the spread of the data about the mean
         * value. It is useful in comparing sets of data which may have the same
         * mean but a different range. For example, the mean of the following two is
         * the same: 15, 15, 15, 14, 16 and 2, 7, 14, 22, 30. However, the second is
         * clearly more spread out. If a set has a low standard deviation, the
         * values are not spread out too much.
         * <p>
         * Example:
         * <p>
         * Find the standard deviation of 4, 9, 11, 12, 17, 5, 8, 12, 14
         * <p>
         * First work out the mean: 10.222
         * <p>
         * Now, subtract the mean individually from each of the numbers in the set
         * and square the result. This is equivalent to the (x - xbar)² step.
         * <p>
         * 
         * <pre>
         * x          4     9     11    12    17    5     8     12    14
         * (x - x)²   38.7  1.49  0.60  3.16  45.9  27.3  4.94  3.16  14.3
         * </pre>
         * 
         * <p>
         * Now add up these results (this is the 'sigma' in the formula): 139.55
         * Divide by n-1. n is the number of values, so in this case is 8: 17.44 And
         * finally, square root this: 4.18
         * 
         * @return the standard deviation
         */
        public Value getStandardDeviation() {
                DataSet ds = null;
                Value res = null;
                Value sigma = null;
                int count = 0;

                ds = getSquaredDistance();

                if (ds != null) {
                        sigma = ds.getSummation();
                        count = ds.getCount();

                        log.debug("Sigma = " + sigma + ", count = " + count);
                }

                if (count > 1 && sigma != null) {
                        res = new Value(Math.sqrt(sigma.doubleValue() / (count - 1)));
                }

                return res;
        }

        ////////////////////////////////////////////////////////////////////////////
        // /
        // DataSet Functionality
        ////////////////////////////////////////////////////////////////////////////
        // /

        public DataSet getLargest(int Nth) {
                DataSet ds = null;

                if (Nth > 0) {
                        ds = getCopy();
                }

                if (ds != null) {
                        ds.setSorted(true);
                        ds = ds.getLast(Nth);
                }

                return ds;
        }

        /**
         * Will create a new <tt>DataSet</tt> with random values chosen from this
         * set.
         * <p>
         * Any value contained in this data-set may be selected and included more
         * than once.
         * <p>
         * The new data-set will not have the same sort or inversion states as this
         * data-set.
         * 
         * @param dataset
         *            instance to get values from
         * @param count
         *            number of values to include
         * @return a new <tt>DataSet</tt> instance
         */
        public DataSet getRandomValues(int count) {
                DataSet ds = null;

                if (count > 0) {
                        ds = new DataSet();

                        for (int x = 0; x < count; x++) {
                                ds.addValue(getRandomValue());
                        }
                }

                return ds;
        }

        /**
         * Creates a new sorted data set with the containing values.
         * 
         * @return a sorted DataSet object
         */
        public DataSet getSorted() {
                DataSet ds = null;

                ds = getCopy();
                ds.setSorted(true);

                return ds;
        }

        /**
         * Will create a new data set containing only the first value of this data
         * set.
         * <p>
         * If there are no values in the data set, no data set will be returned.
         * 
         * @return a new data set instance
         */
        public synchronized DataSet getFirst() {
                return getFirst(1);
        }

        /**
         * Will create a new data set containing the first 'Nth' values of this data
         * set.
         * <p>
         * If the requested size is larger than the number of values currently in
         * the data set, then only the available values will be returned.
         * 
         * @param size
         *            number of values to be taken
         * @return a new data set instance
         */
        public synchronized DataSet getFirst(int Nth) {
                DataSet ds = null;
                int count = 0;

                count = getCount();

                if (count > 0 && Nth > 0) {
                        ds = new DataSet(values.subList(0, Math.min(Nth, count)));
                }

                return ds;
        }

        /**
         * Will create a new data set containing only the last value of this data
         * set.
         * <p>
         * If there are no values in the data set, no data set will be returned.
         * 
         * @return a new data set instance
         */
        public synchronized DataSet getLast() {
                return getLast(1);
        }

        /**
         * Will create a new data set containing the 'Nth' last values of this data
         * set.
         * <p>
         * If the requested size is larger than the number of values currently in
         * the data set, then only the available values will be returned.
         * 
         * @param size
         *            number of values to be taken
         * @return a new data set instance
         */
        public synchronized DataSet getLast(int Nth) {
                DataSet ds = null;
                int count = 0;
                int start = 0;

                count = getCount();

                if (count > 0 && Nth > 0) {
                        start = count - Nth;
                        ds = new DataSet(values.subList(start > 0 ? start : 0, count));
                }

                return ds;
        }

        ////////////////////////////////////////////////////////////////////////////
        // /
        // Copy Functionality
        ////////////////////////////////////////////////////////////////////////////
        // /

        /**
         * Create and return a 'shallow' copy of this instance. All <tt>Value</tt>
         * instances will be referenced rather than duplicated.
         * 
         * @return a new <tt>DataSet</tt> instance
         */
        public DataSet getCopy() {
                return getCopy(false);
        }

        /**
         * Create and return a copy of this data-set. You must choose between a
         * 'shallow' copy or a 'deep' copy.
         * <p>
         * When a 'shallow' copy is requested (by specifying <tt>false</tt>), all
         * <tt>Value</tt> instances will only be referenced (rather than
         * duplicated).
         * <p>
         * When a 'deep' copy is requested (by specifying <tt>true</tt>), all
         * <tt>Value</tt> instances will be duplicated rather than referenced.
         * <p>
         * With a deep copy, if one or more values change in the original copy, the
         * correstponding values will not be changed in the new copy.
         * <p>
         * TODO: Confirm the two types of copy actually work!
         * 
         * @param deepCopy
         *            <tt>true</tt> will create copies, <tt>false</tt> will create
         *            references.
         * @return a new <tt>DataSet</tt> instance
         */
        public synchronized DataSet getCopy(boolean deepCopy) {
                DataSet ds = null;

                ds = new DataSet();

                if (deepCopy) {
                        // Duplicate each value
                        for (int i = 0; i < values.size(); i++) {
                                ds.add(new Value(get(i)));
                        }
                } else {
                        ds.values = new ArrayList(values);
                }

                ds.setReversed(isReversed());
                ds.setSorted(isSorted());

                return ds;
        }

        ////////////////////////////////////////////////////////////////////////////
        // /
        // POJO Functionality
        ////////////////////////////////////////////////////////////////////////////
        // /

        /**
         * Compares the specified object with this data set for equality. Returns
         * <tt>true</tt> if the given object is also a value and the two values
         * represent the same data set.
         * 
         * @param o
         *            object to be compared for equality with this value.
         * @return <tt>true</tt> if the specified object is equal to this data set.
         */
        public boolean equals(Object o) {
                if (!(o instanceof DataSet)) {
                        return false;
                }

                if (o == this) {
                        return true;
                }

                DataSet ds = (DataSet) o;

                return new EqualsBuilder().append(values, ds.values).append(isSorted,
                                ds.isSorted).append(isReversed, ds.isReversed).isEquals();
        }

        /**
         * Returns the hash code value for this data set.
         * 
         * @return the hash code value for this data set.
         * @see Object#hashCode()
         * @see Object#equals(Object)
         * @see #equals(Object)
         */
        // public int hashCode() {
        // // Pick a hard-coded, randomly chosen, non-zero, odd number
        // // ideally different for each class
        // return new HashCodeBuilder(841652161,
        // 23735669).append(super.hashCode()).append(isSorted).append(isReversed).
        // append(isModified).toHashCode();
        // }
        /**
         * Allows comparission between object instances.
         * 
         * @param o
         *            is the object instance to be compared to
         * @return true if they are the same, false otherwise
         */
        public int compareTo(Object o) {
                DataSet x = (DataSet) o;

                return new CompareToBuilder().append(values, x.values).append(isSorted,
                                x.isSorted).append(isReversed, x.isReversed).toComparison();
        }

        /**
         * Will print all the value instances contained within for simple visual
         * output.
         */
        public String printValues() {
                StringBuffer buf = new StringBuffer();
                int count = 0;

                Iterator it = getIterator();

                while (it.hasNext()) {
                        Value v = (Value) it.next();

                        if (v != null) {
                                buf.append(v.getValue());
                        } else {
                                buf.append("NULL");
                        }

                        if (++count != getCount()) {
                                buf.append(", ");
                        }
                }

                return buf.toString();
        }

        /**
         * Returns a string representation of the object. In general, the
         * <code>toString</code> method returns a string that 'textually represents'
         * this object. The result should be a concise but informative
         * representation that is easy for a person to read. It is recommended that
         * all subclasses override this method.
         * 
         * @return a string representation of the object.
         * @author Christian Jean (wasp@jeach.com)
         * @since 1.0.0.1, ICR-12342, Sun Jan 30 14:10:56 EST 2005
         */
        public String toString() {
                ToString buf = new ToString(this);

                buf.add("Count", getCount());
                buf.add("Sorted", isSorted());
                buf.add("Reversed", isReversed());
                buf.add("Dirty", isDirty);
                buf.add("Modified", isModified());
                buf.add("Data", getValues());

                return (buf.toString());
        }
}