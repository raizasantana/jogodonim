/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aluno
 */
public class Status {
    
    public static final Status SIM = new Status("SIM");
    public static final Status NAO = new Status("NAO");
    public static final Status CONECTAR = new Status("CONECTAR");
    public static final Status JOGO_INICIADO = new Status("JOGO_INICIADO");
    public static final Status MEU_TURNO = new Status("MEU_TURNO");
    public static final Status TURNO = new Status("TURNO");
    public static final Status TELA = new Status("TELA");
    public static final Status JOGO_ACABOU = new Status("JOGO_ACABOU");
    public static final Status FIM = new Status("FIM");
        
    private String tipo;

    private Status(String tipo) {
            this.tipo = tipo;
    }


    public static Status createStatus(String tipo) {
            if("SIM".equals(tipo))
                    return Status.SIM;
            else if("NAO".equals(tipo))
                    return Status.NAO;
            else if("CONECTAR".equals(tipo))
                    return Status.CONECTAR;
            else if("JOGO_INICIADO".equals(tipo))
                    return Status.JOGO_INICIADO;
            else if("MEU_TURNO".equals(tipo))
                    return Status.MEU_TURNO;
            else if("TURNO".equals(tipo))
                    return Status.TURNO;
            else if("TELA".equals(tipo))
                    return Status.TELA;
            else if("JOGO_ACABOU".equals(tipo))
                    return Status.JOGO_ACABOU;
            else if("FIM".equals(tipo))
                    return Status.FIM;
            
            return null;
    }

    public boolean check(String tipo) {
            if(this.tipo.equals(tipo))
                    return true;

            return false;
    }

    public String getValor() {
        return tipo;
    }

}
