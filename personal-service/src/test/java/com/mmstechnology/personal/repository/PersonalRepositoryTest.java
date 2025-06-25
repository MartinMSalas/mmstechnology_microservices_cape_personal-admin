package com.mmstechnology.personal.repository;

import com.mmstechnology.personal.model.Personal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersonalRepositoryTest {

    @Autowired
    private PersonalRepository repository;

    @Test
    void shouldSaveAndRetrievePersonal() {
        Personal p = new Personal();
        p.setCuil("20123456789");
        p.setDni("12345678");
        p.setNombres("Juan");
        p.setApellidos("Pérez");
        p.setLegajo("LEG123");
        p.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        p.setFechaIngreso(LocalDate.of(2020, 1, 1));
        p.setUltimaActualizacionRealizadaPor("admin");

        Personal saved = repository.save(p);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCuil()).isEqualTo("20123456789");
    }
}
