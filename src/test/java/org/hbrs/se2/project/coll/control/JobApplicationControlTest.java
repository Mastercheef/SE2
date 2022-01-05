package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.repository.JobApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationControlTest {

    @InjectMocks
    JobApplicationControl jobApplicationControl;

    @Mock
    JobApplicationRepository jobApplicationRepository;

    @Mock
    JobApplicationDTO jobApplicationDTO;

    @Test
    void loadJobApplication() {
        when(jobApplicationRepository.findJobApplicationById(100)).thenReturn(jobApplicationDTO);
        assertEquals(jobApplicationDTO , jobApplicationControl.loadJobApplication(100));
        assertTrue(jobApplicationControl.loadJobApplication(100) instanceof  JobApplicationDTO);
    }
}