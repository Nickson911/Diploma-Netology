package ru.netology.netologydiploma.reposipory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.netology.netologydiploma.exceprion.FileException;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FileRepository.class})
@ExtendWith(SpringExtension.class)
class FileRepositoryTest {
    @Autowired
    private FileRepository fileRepository;

    @Test
    void testSaveFileInvalidDataFileException() {
        try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
            mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
                    .thenThrow(new FileException(" "));
            assertThrows(FileException.class, () -> fileRepository.saveFile("Login", "file.txt",
                    new MockMultipartFile("Name", new ByteArrayInputStream("Word".getBytes()))));
            mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
        }
    }

    @Test
    void testGetFile() {
        assertThrows(FileException.class, () -> fileRepository.getFile("Login1", "file.txt"));
    }

    @Test
    void testRenameFileInvalidData() {
        assertThrows(FileException.class, () -> fileRepository.renameFile("Login1", "file.txt", "file.txt"));
    }

    @Test
    void testDeleteFile() {
        assertThrows(FileException.class, () -> fileRepository.deleteFile("Login2", "file.txt"));
    }
}