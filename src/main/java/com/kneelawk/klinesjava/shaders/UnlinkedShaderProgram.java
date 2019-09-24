package com.kneelawk.klinesjava.shaders;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL20C.*;

/**
 * Supplies arguments for linking a ShaderProgram.
 *
 * @see ShaderProgram
 * @see ShaderComponent
 */
public class UnlinkedShaderProgram {
    private String name;
    private final HashSet<ShaderComponent> components = Sets.newHashSet();

    /**
     * Creates this UnlinkedShaderProgram with a default name and no {@link ShaderComponent}s.
     */
    public UnlinkedShaderProgram() {
        name = "unknown";
    }

    /**
     * Creates this UnlinkedShaderProgram with the given name and {@link ShaderComponent}s.
     *
     * @param name       the name of this UnlinkedShaderProgram.
     * @param components the collection of components in this UnlinkedShaderProgram.
     */
    public UnlinkedShaderProgram(String name, Collection<ShaderComponent> components) {
        this.name = name;
        this.components.addAll(components);
    }

    /**
     * Gets this program's name.
     * <p>
     * The name of a shader program is only used for logs and error messages.
     *
     * @return the name of this UnlinkedShaderProgram.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this program's name.
     * <p>
     * The name of a shader program is only used for logs and error messages.
     *
     * @param name the name of this UnlinkedShaderProgram.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets a set of all the {@link ShaderComponent}s in this UnlinkedShaderProgram.
     *
     * @return a set of all the {@link ShaderComponent}s in this UnlinkedShaderProgram.
     */
    public Set<ShaderComponent> getComponents() {
        return components;
    }

    /**
     * Sets the set of {@link ShaderComponent}s in this UnlinkedShaderProgram.
     *
     * @param components the new set of {@link ShaderComponent}s for this UnlinkedShaderProgram.
     * @return this
     */
    public UnlinkedShaderProgram setComponents(ShaderComponent... components) {
        this.components.clear();
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    /**
     * Sets the set of {@link ShaderComponent}s in this UnlinkedShaderProgram.
     *
     * @param components the new set of {@link ShaderComponent}s for this UnlinkedShaderProgram.
     * @return this
     */
    public UnlinkedShaderProgram setComponents(Collection<ShaderComponent> components) {
        this.components.clear();
        this.components.addAll(components);
        return this;
    }

    /**
     * Add a single {@link ShaderComponent} to this UnlinkedShaderProgram.
     *
     * @param component the {@link ShaderComponent} to add.
     * @return this
     */
    public UnlinkedShaderProgram addComponent(ShaderComponent component) {
        components.add(component);
        return this;
    }

    /**
     * Add an array of {@link ShaderComponent}s to this UnlinkedShaderProgram.
     *
     * @param components the {@link ShaderComponent}s to add.
     * @return this
     */
    public UnlinkedShaderProgram addComponents(ShaderComponent... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    /**
     * Add a collection of {@link ShaderComponent}s to this UnlinkedShaderProgram.
     *
     * @param components the {@link ShaderComponent}s to add.
     * @return this
     */
    public UnlinkedShaderProgram addComponents(Collection<ShaderComponent> components) {
        this.components.addAll(components);
        return this;
    }

    /**
     * Link this UnlinkedShaderProgram into a {@link ShaderProgram}.
     *
     * @return the linked {@link ShaderProgram}.
     * @throws ProgramLinkException if there was an error while linking.
     * @see ShaderProgram
     */
    public ShaderProgram link() throws ProgramLinkException {
        return linkProgram(name, components);
    }

    /**
     * Link the given {@link ShaderComponent}s into a {@link ShaderProgram}.
     *
     * @param name       the name of this program for the purpose of logging and error messages.
     * @param components the collection of components to link.
     * @return the linked {@link ShaderProgram}.
     * @throws ProgramLinkException if there was an error while linking.
     * @see ShaderComponent
     * @see ShaderProgram
     */
    public static ShaderProgram linkProgram(String name, Iterable<ShaderComponent> components)
            throws ProgramLinkException {
        int id = glCreateProgram();

        components.forEach(component -> glAttachShader(id, component.getId()));

        glLinkProgram(id);

        components.forEach(component -> glDetachShader(id, component.getId()));

        String log = glGetProgramInfoLog(id);
        if (!log.isEmpty()) {
            System.err.println(log);
        }

        int status = glGetProgrami(id, GL_LINK_STATUS);

        if (status != GL_TRUE) {
            throw new ProgramLinkException("Unable to link shader: " + name + ", log: " + log + ", status: " + status);
        }

        return new ShaderProgram(id);
    }
}
