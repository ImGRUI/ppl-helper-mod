package ru.kelcuprum.pplhelper.command;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.pplhelper.PepelandHelper;

import java.util.concurrent.CompletableFuture;

public class FollowsArgumentType implements ArgumentType<String> {
    public static String getWorld(final CommandContext<?> context, final String name) {
        return context.getArgument(name, String.class);
    }
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

    @Override
    public String toString() {
        return "string()";
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for(String world : (PepelandHelper.config.toJSON().has("coordinates") ? PepelandHelper.config.toJSON().getAsJsonObject("coordinates") : new JsonObject()).keySet())
            builder.suggest(String.format("\"%s\"", world));
        return builder.buildFuture();
    }
}
