package com.vorobyev.fwb.controller.command;

import com.vorobyev.fwb.command.impl.*;
import com.vorobyev.fwb.controller.command.impl.*;

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
    GO_TO_PUBLICATION(new GoToPublicationCommand()),
    TAKE_FILE(new TakeFileCommand()),
    CHANGE_AVATAR(new ChangeAvatarImageCommand()),
    CREATE_PUBLICATION(new CreatePublicationCommand()),
    ADD_COMMEND(new AddCommendCommand()),
    LIKE(new LikeCommand()),
    UNLIKE(new UnlikeCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    GO_CREATE_PUBLICATION(new GoCreatePublicationCommand()),
    GO_EDIT_PUBLICATION(new GoEditPublicationCommand()),
    SHOW_PUBLICATIONS(new ShowPublicationsCommand()),
    SHOW_ADMIN_PANEL(new ShowAdminPanelCommand());

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
