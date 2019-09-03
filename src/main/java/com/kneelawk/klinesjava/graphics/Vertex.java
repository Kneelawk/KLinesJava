package com.kneelawk.klinesjava.graphics;

import org.joml.Matrix4fc;
import org.joml.Vector3fc;

import java.util.Objects;

public class Vertex {
    private final Vector3fc position;
    private final Vector3fc color;
    private final Matrix4fc transform;

    public Vertex(Vector3fc position, Vector3fc color, Matrix4fc transform) {
        this.position = position;
        this.color = color;
        this.transform = transform;
    }

    public Vector3fc getPosition() {
        return position;
    }

    public Vector3fc getColor() {
        return color;
    }

    public Matrix4fc getTransform() {
        return transform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return position.equals(vertex.position) &&
                color.equals(vertex.color) &&
                transform.equals(vertex.transform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, color, transform);
    }
}
