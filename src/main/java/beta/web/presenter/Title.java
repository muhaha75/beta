/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

/**
 *
 * @author tjark.uschakow
 */
public enum Title {
    
        DR("Dr."),PROF("Prof."),PROFDR("Prof. Dr.");
        
        private final String sign;

    private Title(String sign){
    this.sign = sign;
    }    
        
    public String getSign() {
        return sign;
    }
  
}
