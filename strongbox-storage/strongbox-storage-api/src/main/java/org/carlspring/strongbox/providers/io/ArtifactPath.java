package org.carlspring.strongbox.providers.io;

import java.io.File;
import java.nio.file.Path;

import org.carlspring.strongbox.artifact.coordinates.ArtifactCoordinates;
import org.carlspring.strongbox.io.ArtifactFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This implementation defines an ArtifactFile path with {@link ArtifactCoordinates}. <br>
 * Against {@link RepositoryPath} it can be only a File (not a directory).
 *
 * @author Sergey Bespalov
 */
public class ArtifactPath
        extends RepositoryPath
{

    private static final Logger logger = LoggerFactory.getLogger(ArtifactPath.class);

    private ArtifactCoordinates coordinates;

    public ArtifactPath(ArtifactCoordinates coordinates,
                        Path target,
                        RepositoryFileSystem fileSystem)
    {
        super(target, fileSystem);
        this.coordinates = coordinates;
    }

    public ArtifactCoordinates getCoordinates()
    {
        return coordinates;
    }

    public ArtifactFile toFile()
    {
        ArtifactCoordinates coordinatesLocal = getCoordinates();
        File source;
        try
        {
            source = getTarget().toFile();
        }
        catch (UnsupportedOperationException e)
        {
            logger.warn(String.format("toFile() unsupported: coordinates-[%s]; pathTarget-[%s]",
                                      coordinatesLocal,
                                      getTarget().getClass()
                                                 .getName()),
                        e);
            throw e;
        }
        return new ArtifactFile(getRoot().toString(), coordinatesLocal);
    }

}
