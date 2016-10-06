/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enelar.pr100_pr001;

import com.enelar.libreria_enelar.conex;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USERPC
 */
public class pr100_pr001Model {

    public Map<String, Object> DatosConvenio(String param1) {
        Map<String, Object> salida = new HashMap();
        try (conex db = new conex();) {
            String sql = "select a.cod_convenio, a.nitter, b.razsoc, a.descripcion,\n"
                    + "   to_char(a.fecha_inicial,'dd/mm/yyyy')fecha_inicial, \n"
                    + "   to_char(a.fecha_final,'dd/mm/yyyy') fecha_final, a.porc, a.plazo_d\n"
                    + "from convenio_ter a, tercero b\n"
                    + "where a.nitter   = b.nitter\n"
                    + "and cod_convenio = :convenio";
            List<Map<String, Object>> resultado = db.select(db, sql, new Object[]{param1}, false);
            db.cerrar();
            salida.put("mensaje", "OK");
			salida.put("respuesta", "OK");
            salida.put("data", resultado);
            salida.put("cantidad", resultado.size());
            salida.put("estado", 1);
            return salida;
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(pr100_pr001Model.class.getName()).log(Level.SEVERE, null, ex);
            salida.put("mensaje", "NO SE HAN OBTENIDO DATOS");
            salida.put("respuesta", ex.getLocalizedMessage());
			salida.put("data", null);
            salida.put("cantidad", "0");
            salida.put("estado", "0");
            return salida;
        }
    }
}
