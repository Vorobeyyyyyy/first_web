package com.vorobyev.fwb.controller.command;

import com.vorobyev.fwb.controller.command.impl.*;
import com.vorobyev.fwb.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public enum CommandProvider implements Command {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    GO_TO_LOGIN(new GoLoginCommand()),
    GO_TO_REGISTER(new GoRegisterCommand()),
    LOGOUT(new LogoutCommand()),
    GO_TO_PROFILE(new GoProfileCommand()),
    SET_LOCALE(new LocaleCommand()),
    GO_TO_PUBLICATION(new GoPublicationCommand()),
    TAKE_FILE(new TakeFileCommand()),
    CHANGE_AVATAR(new ChangeAvatarImageCommand()),
    UPDATE_PUBLICATION(new UpdatePublicationCommand()),
    ADD_COMMEND(new AddCommendCommand()),
    LIKE(new LikeCommand()),
    UNLIKE(new UnlikeCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    GO_CREATE_PUBLICATION(new GoCreatePublicationCommand()),
    GO_EDIT_PUBLICATION(new GoEditPublicationCommand()),
    SHOW_PUBLICATIONS(new ShowPublicationsCommand()),
    SHOW_ADMIN_PANEL_USERS(new ShowAdminPanelUsersCommand()),
    SHOW_ADMIN_PANEL_COMMENDS(new ShowAdminPanelCommendsCommand()),
    SHOW_ADMIN_PANEL_PUBLICATIONS(new ShowAdminPanelPublicationsCommand()),
    REMOVE_PUBLICATION(new RemovePublicationCommand()),
    REMOVE_COMMEND(new RemoveCommendCommand()),
    REMOVE_USER(new RemoveUserCommand());

    Command command;

    CommandProvider(Command command) {
        this.command = command;
    }

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        return command.perform(request, response);
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return command.getAllowedAccessLevels();
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
