package com.vorobyev.first_web.command;

import com.vorobyev.first_web.command.impl.*;

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
    SET_LOCALE(new LocaleCommand());

    Command command;

    CommandProvider(Command command) {
        this.command = command;
    }

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        return command.preform(request, response);
    }

    public static Optional<Command> defineCommand(String commandName) {
        if (commandName == null) {
            return Optional.empty();
        }

        commandName = commandName.toUpperCase(Locale.ROOT);
        Command command;
        try {
            command = CommandProvider.valueOf(commandName);
        } catch (IllegalArgumentException exception) {
            command = null;
        }
        return Optional.ofNullable(command);
    }
}
