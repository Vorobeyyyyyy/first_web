package com.vorobyev.fwb.command;

import com.vorobyev.fwb.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

public enum CommandProvider implements Command {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    GO_TO_LOGIN(new GoToLoginCommand()),
    GO_TO_REGISTER(new GoToRegisterCommand()),
    LOGOUT(new LogoutCommand()),
    GO_TO_PROFILE(new GoToProfileCommand()),
    SET_LOCALE(new LocaleCommand()),
    GO_TO_MAIN(new GoToMainCommand()),
    GO_TO_PUBLICATION(new GoToPublicationCommand()),
    TAKE_FILE(new TakeFile()),
    CHANGE_AVATAR(ChangeAvatarImage.INSTANCE);

    Command command;

    CommandProvider(Command command) {
        this.command = command;
    }

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        return command.preform(request, response);
    }

    public static Optional<Command> defineCommand(String commandName) {
        if (commandName == null || commandName.isBlank()) {
            return Optional.empty();
        }

        commandName = commandName.toUpperCase(Locale.ROOT);
        Optional<Command> optionalCommand;
        try {
            optionalCommand = Optional.of(CommandProvider.valueOf(commandName));
        } catch (IllegalArgumentException exception) {
            optionalCommand = Optional.empty();
        }
        return optionalCommand;
    }
}
