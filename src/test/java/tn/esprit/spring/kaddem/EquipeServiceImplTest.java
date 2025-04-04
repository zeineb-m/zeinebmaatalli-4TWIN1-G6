package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    private Equipe equipe1;
    private Equipe equipe2;

    @BeforeEach
    void setUp() {
        equipe1 = new Equipe(1, "Team A", Niveau.JUNIOR);
        equipe2 = new Equipe(2, "Team B", Niveau.SENIOR);
    }

    @Test
    void testRetrieveAllEquipes() {
        when(equipeRepository.findAll()).thenReturn(Arrays.asList(equipe1, equipe2));
        List<Equipe> equipes = equipeService.retrieveAllEquipes();
        assertEquals(2, equipes.size());
        verify(equipeRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEquipe() {
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe1));
        Equipe equipe = equipeService.retrieveEquipe(1);
        assertNotNull(equipe);
        assertEquals("Team A", equipe.getNomEquipe());
        verify(equipeRepository, times(1)).findById(1);
    }

    @Test
    void testAddEquipe() {
        when(equipeRepository.save(equipe1)).thenReturn(equipe1);
        Equipe savedEquipe = equipeService.addEquipe(equipe1);
        assertNotNull(savedEquipe);
        assertEquals("Team A", savedEquipe.getNomEquipe());
        verify(equipeRepository, times(1)).save(equipe1);
    }

    @Test
    void testDeleteEquipe() {
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe1));
        doNothing().when(equipeRepository).delete(equipe1);
        equipeService.deleteEquipe(1);
        verify(equipeRepository, times(1)).delete(equipe1);
    }

    @Test
    void testUpdateEquipe() {
        when(equipeRepository.save(equipe1)).thenReturn(equipe1);
        Equipe updatedEquipe = equipeService.updateEquipe(equipe1);
        assertNotNull(updatedEquipe);
        assertEquals("Team A", updatedEquipe.getNomEquipe());
        verify(equipeRepository, times(1)).save(equipe1);
    }
}
