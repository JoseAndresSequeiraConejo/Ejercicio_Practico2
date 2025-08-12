/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service.impl;

/**
 *
 * @author Jose Sequeira
 */

import com.caso2.dao.CarroDao;
import com.caso2.dao.FacturaDao;
import com.caso2.dao.UsuarioDao;
import com.caso2.dao.VentaDao;
import com.caso2.domain.Carro;
import com.caso2.domain.Factura;
import com.caso2.domain.Item;
import com.caso2.domain.Usuario;
import com.caso2.domain.Venta;
import com.caso2.service.ItemService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpSession session;

    @Override
    public List<Item> gets() {
        @SuppressWarnings("unchecked")
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        return listaItems;
    }

    @Override
    public Item get(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems != null) {
            for (Item i : listaItems) {
                if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                    return i;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems != null) {
            int pos = -1;
            boolean existe = false;
            for (Item i : listaItems) {
                pos++;
                if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                listaItems.remove(pos);
                session.setAttribute("listaItems", listaItems);
            }
        }
    }

    @Override
    public void save(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems == null) listaItems = new ArrayList<>();

        boolean existe = false;
        for (Item i : listaItems) {
            if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                existe = true;
                // Si manejas existencias, descomenta la validación:
                // if (i.getCantidad() < i.getExistencias()) {
                //     i.setCantidad(i.getCantidad() + 1);
                // }
                i.setCantidad(i.getCantidad() + 1);
                break;
            }
        }
        if (!existe) {
            item.setCantidad(1);
            listaItems.add(item);
        }
        session.setAttribute("listaItems", listaItems);
    }

    @Override
    public void update(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems != null) {
            for (Item i : listaItems) {
                if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                    i.setCantidad(item.getCantidad());
                    session.setAttribute("listaItems", listaItems);
                    break;
                }
            }
        }
    }

    @Autowired private UsuarioDao usuarioDao;
    @Autowired private CarroDao carroDao;
    @Autowired private FacturaDao facturaDao;
    @Autowired private VentaDao ventaDao;

    @Override
    public void facturar() {
        // 1) Usuario autenticado
        String username = "";
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails ud) username = ud.getUsername();
        else if (principal != null) username = principal.toString();
        if (username.isBlank()) return;

        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) return;

        // 2) Crear la factura y mantener la variable en el scope del método
        Factura factura = facturaDao.save(new Factura(usuario.getIdUsuario()));

        // 3) Procesar ítems del carrito
        @SuppressWarnings("unchecked")
        List<Item> lista = (List<Item>) session.getAttribute("listaItems");
        if (lista == null || lista.isEmpty()) return;

        double total = 0.0;
        for (Item i : lista) {
            // (Opcional) validar que el carro existe
            carroDao.getReferenceById(i.getIdCarro());

            // Registrar la venta (ajusta el constructor de Venta si difiere)
            Venta venta = new Venta(
                factura.getIdFactura(), // <-- ahora sí existe
                i.getIdCarro(),
                i.getPrecio(),
                1 // cada auto es único
            );
            ventaDao.save(venta);

            total += i.getPrecio();
        }

        // 4) Actualizar total y guardar la factura
        factura.setTotal(total);
        facturaDao.save(factura);

        // 5) Limpiar carrito
        lista.clear();
        session.setAttribute("listaItems", lista);
    }


    @Override
    public double getTotal() {
        @SuppressWarnings("unchecked")
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems == null || listaItems.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Item i : listaItems) {
            total += i.getCantidad() * i.getPrecio();
        }
        return total;
    }


}


