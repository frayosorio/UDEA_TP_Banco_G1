package servicios;

import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class MascaraAlfaNumerica extends DocumentFilter {

    @Override
    public void replace(FilterBypass filtro, int offset, int longitud, String caracterDigitado, AttributeSet atributos)
            throws BadLocationException {
        Document documento = filtro.getDocument();
        String textoActual = documento.getText(0, documento.getLength());
        textoActual += caracterDigitado;

        if (textoActual.matches("[a-zA-Z0-9]+")) {
            super.insertString(filtro, offset, caracterDigitado, atributos);
        }

    }

}


