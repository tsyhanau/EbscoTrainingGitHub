package com.epam.utils;

import com.epam.services.FileHandlerService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CustomArgumentsGenerator implements ArgumentsProvider {

    public static final FileTypes ENUM_TYPE_OF_FILE = FileTypes.TXT;

    FileHandlerService fileHandlerService = new FileHandlerService();
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(fileHandlerService.getNewFile(ENUM_TYPE_OF_FILE))
                .map(Arguments::of);
    }
}