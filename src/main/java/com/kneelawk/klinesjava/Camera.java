package com.kneelawk.klinesjava;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Camera {
    private final Vector3f location;
    private final Vector3f target;
    private final Vector3f up;
    private float fovy;
    private float aspect;
    private float zNear;
    private float zFar;

    private final Matrix4f view;
    private final Matrix4f projection;

    private final Matrix4f pv;

    /**
     * Default Camera constructor that initializes with default values.
     * <p>
     * This also initializes the camera's matrices.
     */
    public Camera() {
        this(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0), 45f, 1f, 0.01f, 100f);
    }

    /**
     * Initializes the camera with the given values.
     * <p>
     * This also initializes the camera's matrices.
     *
     * @param location The location of the camera. (This is the 'eye')
     * @param target   The location the camera is looking at.
     * @param up       The orientation of the camera. This is the vector for which way the camera should try to have as an up direction.
     * @param fovy     The vertical field of view of the camera.
     * @param aspect   The aspect ratio (height / width) of the view.
     * @param zNear    The farthest z value that should be rendered.
     * @param zFar     The closest z value that should be rendered.
     */
    public Camera(Vector3f location, Vector3f target, Vector3f up, float fovy, float aspect, float zNear, float zFar) {
        this.location = location;
        this.target = target;
        this.up = up;
        this.fovy = fovy;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;

        view = new Matrix4f().setLookAt(location, target, up);
        projection = new Matrix4f().setPerspective(fovy, aspect, zNear, zFar);

        pv = projection.mul(view, new Matrix4f());
    }

    /**
     * Gets the current projection-view matrix of this camera.
     * <p>
     * This does not trigger a re-calculation of this camera's matrices.
     * In order to get updated matrices, one must call {@link #update()} first.
     *
     * @return the current projection-view matrix of this camera.
     */
    public Matrix4f getMatrix() {
        return pv;
    }

    /**
     * Re-calculates this camera's matrices.
     */
    public void update() {
        view.setLookAt(location, target, up);
        projection.setPerspective(fovy, aspect, zNear, zFar);
        projection.mul(view, pv);
    }

    /**
     * Gets this camera's location (eye position) vector.
     *
     * @return this camera's location vector.
     */
    public Vector3f getLocation() {
        return location;
    }

    /**
     * Sets this camera's location (eye position) vector.
     *
     * @param location this camera's location vector.
     */
    public void setLocation(Vector3fc location) {
        this.location.set(location);
    }

    /**
     * Gets this camera's target (position that this camera is looking at) vector.
     *
     * @return this camera's target vector.
     */
    public Vector3f getTarget() {
        return target;
    }

    /**
     * Sets this camera's target (position that this camera is looking at) vector.
     *
     * @param target this camera's target vector.
     */
    public void setTarget(Vector3fc target) {
        this.target.set(target);
    }

    /**
     * Gets this camera's up vector.
     *
     * @return this camera's up vector.
     */
    public Vector3f getUp() {
        return up;
    }

    /**
     * Sets this camera's up vector.
     *
     * @param up this camera's up vector.
     */
    public void setUp(Vector3fc up) {
        this.up.set(up);
    }

    /**
     * Gets this camera's vertical field of view.
     *
     * @return this camera's vertical field of view.
     */
    public float getFovy() {
        return fovy;
    }

    /**
     * Sets this camera's vertical field of view.
     *
     * @param fovy this camera's vertical field of view.
     */
    public void setFovy(float fovy) {
        this.fovy = fovy;
    }

    /**
     * Gets this camera's view aspect (height / width) ratio.
     *
     * @return this camera's view aspect ratio.
     */
    public float getAspect() {
        return aspect;
    }

    /**
     * Sets this camera's view aspect (height / width) ratio.
     *
     * @param aspect this camera's view aspect ratio.
     */
    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

    /**
     * Gets the nearest z value that should be rendered.
     *
     * @return the nearest z value that should be rendered.
     */
    public float getzNear() {
        return zNear;
    }

    /**
     * Sets the nearest z value that should be rendered.
     * <p>
     * This value should not be 0 because an object that close would be rendered with an infinite size.
     *
     * @param zNear the nearest z value that should be rendered.
     */
    public void setzNear(float zNear) {
        this.zNear = zNear;
    }

    /**
     * Gets the farthest z value that should be rendered.
     *
     * @return the farthest z value that should be rendered.
     */
    public float getzFar() {
        return zFar;
    }

    /**
     * Sets the farthest z value that should be rendered.
     *
     * @param zFar the farthest z value that should be rendered.
     */
    public void setzFar(float zFar) {
        this.zFar = zFar;
    }

    /**
     * Gets this camera's view matrix.
     *
     * @return this camera's view matrix.
     */
    public Matrix4f getView() {
        return view;
    }

    /**
     * Sets the vectors that compose this camera's view matrix.
     * <p>
     * Note: This does not re-calculate the view matrix. That must be done ith {@link #update()}.
     *
     * @param location the location of the camera.
     * @param target   the location that this camera is looking at.
     * @param up       the up vector of this camera.
     */
    public void setView(Vector3fc location, Vector3fc target, Vector3fc up) {
        this.location.set(location);
        this.target.set(target);
        this.up.set(up);
    }

    /**
     * Sets the vectors that compose this camera's view matrix.
     * <p>
     * Note: This does not re-calculate the view matrix. That must be done ith {@link #update()}.
     *
     * @param locationX the x component of the location of the camera.
     * @param locationY the y component of the location of the camera.
     * @param locationZ the z component of the location of the camera.
     * @param targetX   the x component of the target of the camera.
     * @param targetY   the y component of the target of the camera.
     * @param targetZ   the z component of the target of the camera.
     * @param upX       the x component of the up vector for the camera.
     * @param upY       the y component of the up vector for the camera.
     * @param upZ       the z component of the up vector for the camera.
     */
    public void setView(float locationX, float locationY, float locationZ, float targetX, float targetY, float targetZ,
                        float upX, float upY, float upZ) {
        location.set(locationX, locationY, locationZ);
        target.set(targetX, targetY, targetZ);
        up.set(upX, upY, upZ);
    }

    /**
     * Gets this camera's projection matrix.
     *
     * @return this camera's projection matrix.
     */
    public Matrix4f getProjection() {
        return projection;
    }

    /**
     * Sets the value that compose this camera's projection matrix.
     * <p>
     * Note: This does not re-calculate the projection matrix. That must be done ith {@link #update()}.
     *
     * @param fovy   the vertical field of view of the camera.
     * @param aspect the aspect (height / width) ratio of the camera.
     * @param zNear  the closest z value that should be rendered.
     * @param zFar   the farthest z value that should be rendered.
     */
    public void setProjection(float fovy, float aspect, float zNear, float zFar) {
        this.fovy = fovy;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;
    }
}
