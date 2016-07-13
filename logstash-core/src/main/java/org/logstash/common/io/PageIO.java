package org.logstash.common.io;

import org.logstash.ackedqueue.Queueable;

import java.io.IOException;
import java.util.List;

public interface PageIO {

    // the concrete class should be constructed with the capacity and dirPath attributes

    // open an existing data container and reconstruct internal state if required
    void open(long minSeqNum, int elementCount) throws IOException;

    // create a new empty data file
    void create() throws IOException;

    // verify if the data container has space for the given number of bytes
    boolean hasSpace(int bytes);

    // write the given bytes to the data container
    void write(byte[] bytes, Queueable element) throws IOException;

    // read up to limit number of items starting at give seqNum
    List<ReadElementValue> read(long seqNum, int limit) throws IOException;

    // @return the data container total capacity in bytes
    int getCapacity();

    // signal that this data page is not active and resources can be released
    void deactivate();

    // signal that this data page is active will be read or written to
    // should do nothing if page is aready active
    void activate();

    // issue the proper data container "fsync" sematic
    void ensurePersisted();

    // delete, unlike, remove data file
    void purge() throws IOException;

    // add debugging details, to be added when creating a new page
    // void setPageHeaderDetails(String details);
}