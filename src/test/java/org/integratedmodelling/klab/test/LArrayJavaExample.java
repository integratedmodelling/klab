package org.integratedmodelling.klab.test;

import java.io.File;

/*--------------------------------------------------------------------------
 *  Copyright 2013 Taro L. Saito
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *--------------------------------------------------------------------------*/

import scala.runtime.AbstractFunction1;
import xerial.larray.LArray;
import xerial.larray.LFloatArray;
import xerial.larray.LIntArray;
import xerial.larray.LIntArrayBuilder;
import xerial.larray.LIterator;
import xerial.larray.buffer.LBuffer;
import xerial.larray.japi.LArrayJ;

/**
 * LArray example in Java
 * @author Taro L. Saito
 */
public class LArrayJavaExample {

    public static void main(String[] args) {

        LFloatArray l = LArrayJ.newLFloatArray(5);

        for(int i=0; i<l.size(); i++) {
            l.update((long)i, (float)i);
        }

        System.out.println(l.mkString(", "));

        // Traverse the elements
        int index = 0;
        for(Object e : l.ji()) {
            System.out.println(String.format("l(%d) = %d", index, (Integer)e));
            index += 1;
        }

        // manipulate LArray
        LIterator<?> l2 = l.map(new AbstractFunction1<Object, Object>(){
            public Object apply(Object v1) {
                return Integer.class.cast(v1) * 10;
            }
        });
        LIterator<?> f = l.filter(new AbstractFunction1<Object, Object>(){
            public Object apply(Object v1) {
                return Integer.class.cast(v1) % 2 == 0;
            }
        });
        LArray s = l.slice(2);

        // Build LArray
        LIntArrayBuilder b = new LIntArrayBuilder();
        for(int i=0; i<10; i += 3)
            b.append(i);
        LIntArray lb = b.result(); // LArray(0, 3, 6, 9)
        System.out.println(lb.mkString(", "));

        // Save to a file
        File file = l.saveTo(new File("target/larray.tmp"));
        file.deleteOnExit();

        // Load from a file
        LArray l3 = LArrayJ.loadLIntArrayFrom(file); // LArray(0, 1, 2, 3, 4)

        // Initialize the array
        l.clear();
        System.out.println(l.mkString(", ")); // 0, 0, 0, 0, 0

        // Release the memory contents
        l.free();


        // Using LBuffer
        LBuffer lbuf = new LBuffer(1000);
        lbuf.putInt(0, 10);
        int ten = lbuf.getInt(0);
        lbuf.address(); // memory address
        lbuf.release(); // deallocate the memory


        System.out.println("done.");
    }
}