/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pincheng.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 *
 * @author User
 */
public class SnmpWalk {
    
    

    /**
     * @param ip
     * @param targetOid
     */
    public static String snmpWalk(String ip, String targetOid) {
        DecimalFormat df = new DecimalFormat("#.00");
        CommunityTarget target = SnmpUtil.createDefault(ip, "public");
        TransportMapping transport = null;
        Snmp snmp = null;
        Integer totalCpuLoading = 0;
        Integer cpuCore = 8;
        try {
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();

            PDU pdu = new PDU();
            OID targetOID = new OID(targetOid);
            pdu.add(new VariableBinding(targetOID));

            boolean finished = false;
            while (!finished) {
                VariableBinding vb = null;
                ResponseEvent respEvent = snmp.getNext(pdu, target);

                PDU response = respEvent.getResponse();

                if (null == response) {
                    System.out.println("responsePDU == null");
                    finished = true;
                    break;
                } else {
                    vb = response.get(0);
                }
                // check finish
                finished = checkWalkFinished(targetOID, pdu, vb);
                if (!finished) {
//                    System.out.println("==== walk each vlaue :");
                    String value = vb.getVariable().toString();
                    if (value.contains(":")) {
                    } else {
//                        System.out.print(vb.getOid());
//                        System.out.print(vb.getVariable());
                        if (vb.getOid().toString().equals("1.3.6.1.2.1.25.2.3.1.6.4")) {
                            //*65536/1024/1024=記憶體使用量MB
                            //8G=記憶體總量  
                            float memoryUsage = Float.valueOf(df.format(vb.getVariable().toLong() * 0.00078125));
                            return String.valueOf(memoryUsage);
                        } else if (vb.getOid().toString().contains("1.3.6.1.2.1.25.3.3.1.2")) {
                            totalCpuLoading += vb.getVariable().toInt();
                        }
                    }

                    // Set up the variable binding for the next entry.
                    pdu.setRequestID(new Integer32(0));
                    pdu.set(0, vb);
                } else {
//                    System.out.println("SNMP walk OID has finished.");
                    snmp.close();
                    float cpuUsage = Float.valueOf(df.format(totalCpuLoading/cpuCore));
                    return String.valueOf(cpuUsage);
                }
            }
            System.out.println("----> demo end <----");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SNMP walk Exception: " + e);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }

        return "0";
    }

    private static boolean checkWalkFinished(OID targetOID, PDU pdu,
            VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
            System.out.println("[true] responsePDU.getErrorStatus() != 0 ");
            System.out.println(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            System.out.println("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
            System.out.println("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            System.out
                    .println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            System.out.println("[true] Variable received is not "
                    + "lexicographic successor of requested " + "one:");
            finished = true;
        }
        return finished;

    }
}
