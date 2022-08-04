package ua.klieshchunov.task.operationImpl;

import ua.klieshchunov.task.JoinOperation;
import ua.klieshchunov.task.rows.DataRow;
import ua.klieshchunov.task.rows.JoinedDataRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LeftJoinOperation<V1, V2, R> implements JoinOperation<DataRow<R,V1>, DataRow<R,V2>, JoinedDataRow<R, V1, V2>> {

    @Override
    public Collection<JoinedDataRow<R, V1, V2>> join(Collection<DataRow<R, V1>> leftCollection, Collection<DataRow<R, V2>> rightCollection) {
        if (leftCollection == null || rightCollection == null)
            throw new IllegalArgumentException("Given parameters contain null values");

        List<JoinedDataRow<R, V1, V2>> storage = new ArrayList<>();

        leftCollection.forEach(left -> rightCollection.stream()
                        .filter(right -> left.key.equals(right.key))
                        .findAny()
                        .ifPresentOrElse(
                                right -> storage.add(new JoinedDataRow<>(left.key, left.value, right.value)),
                                () -> storage.add(new JoinedDataRow<>(left.key, left.value, null))));

        return storage;
    }
}
