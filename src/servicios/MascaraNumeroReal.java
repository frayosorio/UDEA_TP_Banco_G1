package servicios;

import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class MascaraNumeroReal extends DocumentFilter {

    @Override
    public void replace(FilterBypass filtro, int offset, int longitud, String caracterDigitado, AttributeSet atributos)
            throws BadLocationException {
        Document documento = filtro.getDocument();
        String textoActual = documento.getText(0, documento.getLength());
        //System.out.println("caracter digitado=" + caracterDigitado);
        //System.out.println("texto actual=" + textoActual);
        textoActual += caracterDigitado;

        if (textoActual.matches("[0-9]+[.]?[0-9]*")) {
            super.insertString(filtro, offset, caracterDigitado, atributos);
        }

    }

}
