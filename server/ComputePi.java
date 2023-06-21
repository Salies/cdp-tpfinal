/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;

import compute.Compute;
import server.hashing.Hash;
import server.network.ProfileRenderer;
import server.stats.DataStats;

public class ComputePi {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Compute comp = (Compute) registry.lookup(name);
            
            //Pi task = new Pi(45);
            //BigDecimal pi = comp.executeTask(task);

            Hash task = new Hash("md5", "werehog");
            String h = comp.executeTask(task);

            System.out.println(h);

            ProfileRenderer task2 = new ProfileRenderer("João", "joao", "Sou o João", "Porto", 1, 0, 0);
            String html = comp.executeTask(task2);

            //System.out.println(html);
            System.out.println("html ok");

            Double[] data = {3.0, 2.0, 4.0, 2.0, 2.0, 10.0, 19.0, 18.0, 19.0, 8.0, 4.0, 1.0, 1.0, 1.0, 1.0, 1.0, 3.0, 6.0, 7.0, 9.0, 1.0};
            DataStats ds = new DataStats(data, 1);
            HashMap<String, Double> stats = comp.executeTask(ds);
            System.out.println(stats);
            
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }    
}
