package assets;

/**
 * Descriptor for an audio asset (music track or sound effect).
 * Stores the file path and type classification.
 */
public class AudioAsset {

    /** Original filename with extension (.wav, .mid, etc.) */
    public final String filename;

    /** Directory path relative to project root, forward-slash, trailing slash */
    public final String directory;

    /** True = background music track, False = one-shot sound effect */
    public final boolean isMusic;

    /** Human-readable description of the audio */
    public final String description;

    public AudioAsset(String filename, String directory,
                      boolean isMusic, String description) {
        this.filename    = filename;
        this.directory   = directory;
        this.isMusic     = isMusic;
        this.description = description;
    }

    /** Shorthand for a music track */
    public static AudioAsset music(String file, String dir, String desc) {
        return new AudioAsset(file, dir, true, desc);
    }

    /** Shorthand for a sound effect */
    public static AudioAsset sfx(String file, String dir, String desc) {
        return new AudioAsset(file, dir, false, desc);
    }

    /** Full path from project root:  directory + filename */
    public String getFullPath() {
        return directory + filename;
    }

    @Override
    public String toString() {
        return String.format("AudioAsset[%s | %s]",
                filename, isMusic ? "music" : "sfx");
    }
}
