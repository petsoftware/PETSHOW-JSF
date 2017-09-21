package br.com.petshow.web.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.petshow.model.Vacina;

public class VacinaDataModel extends ListDataModel<Vacina> implements SelectableDataModel<Vacina> {

	public VacinaDataModel() {
    }

    public VacinaDataModel(List<Vacina> vacinas) {
        super(vacinas);
    }
	@Override
	public Vacina getRowData(String rowkey) {
		List<Vacina> vacinas = (List<Vacina>) getWrappedData();
        for(Vacina vacina : vacinas) {
            if(vacina.getId() == (Long.parseLong(rowkey)))
                return vacina;
        }
		return null;
	}

	@Override
	public Object getRowKey(Vacina vacina) {
		  return vacina.getId();
	}

}
