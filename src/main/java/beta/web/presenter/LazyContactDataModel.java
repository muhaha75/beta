/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.eao.ContactEao;
import beta.server.entity.Contact;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tjark.uschakow
 */
public class LazyContactDataModel extends LazyDataModel<Contact> {

    private List<Contact> data;
    
    private final Logger L = LoggerFactory.getLogger(LazyContactDataModel.class);
    
    public LazyContactDataModel(List<Contact> datasource) {
       data = datasource;
       this.setRowCount(datasource.size());
       L.info("Count:{}",datasource.size());
       
    }
    
    @Override
    public List<Contact> load(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String,Object> filters){
        List<Contact> list = data.subList(first, first + pageSize);
        
        L.info("Count of sublist:{}",list.size());
        return list;
    }
    
}
