/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author User
 */
public class DashBoardDto {
    private String cpuNormal = "0";
    private String cpuCritical = "0";
    private Integer cpuHealthRate = 0;
    private String memoryNormal = "0";
    private String memoryCritical = "0";
    private Integer memoryHealthRate = 0;

    public String getCpuNormal() {
        return cpuNormal;
    }

    public void setCpuNormal(String cpuNormal) {
        this.cpuNormal = cpuNormal;
    }

    public String getCpuCritical() {
        return cpuCritical;
    }

    public void setCpuCritical(String cpuCritical) {
        this.cpuCritical = cpuCritical;
    }


    public String getMemoryNormal() {
        return memoryNormal;
    }

    public void setMemoryNormal(String memoryNormal) {
        this.memoryNormal = memoryNormal;
    }

    public String getMemoryCritical() {
        return memoryCritical;
    }

    public void setMemoryCritical(String memoryCritical) {
        this.memoryCritical = memoryCritical;
    }

    public Integer getCpuHealthRate() {
        return cpuHealthRate;
    }

    public void setCpuHealthRate(Integer cpuHealthRate) {
        this.cpuHealthRate = cpuHealthRate;
    }

    public Integer getMemoryHealthRate() {
        return memoryHealthRate;
    }

    public void setMemoryHealthRate(Integer memoryHealthRate) {
        this.memoryHealthRate = memoryHealthRate;
    }

    
}
