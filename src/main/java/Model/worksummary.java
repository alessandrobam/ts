package Model;


import Util.Util;
import org.apache.commons.lang3.StringUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abarbosa
 */
public class worksummary
{
    private String rolename;
    private int horas;
    private int produtivo;

    public String getRolename() {
        return rolename;
    }

    public int getProdutivo() {
        return produtivo;
    }

    public void setProdutivo(int produtivo) {
        this.produtivo = produtivo;
    }

    public int getHoras() {
        return horas;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public worksummary(String rolename, int horas, int produtivo) {
        this.rolename = rolename;
        this.horas = horas;
        this.produtivo = produtivo;
    }
        
 public String getTotaltext()
    {
        return (StringUtils.leftPad(Util.MinutesToHoursText(Long.valueOf(this.getHoras())), 5, "0"));
    }    
 
 
    
}
