
// Incoming vertex position
in vec2 position;

uniform mat3 model_matrix;

uniform mat3 view_matrix;

out vec2 localPosition;

void main() {
	// Output the local position
	localPosition = position;

	// The global position is in homogenous coordinates
    vec3 globalPosition = model_matrix * vec3(position, 1);

    // The position in camera coordinates
    vec3 viewPosition = view_matrix * globalPosition;

    // We must convert from a homogenous coordinate in 2D to a homogenous
    // coordinate in 3D.
    gl_Position = vec4(viewPosition.xy, 0, 1);
}
