import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int position;

    void clear() {
        for (int i = 0; i < position; i++) {
            storage[i] = null;
        }
        position = 0;
    }

    void save(Resume resume) {
        if (position == storage.length) {
            System.out.println("Хранилище резюме заполнено");
            return;
        }
        int indexOfResume = getIndexOfResume(resume.getUuid());
        if (indexOfResume >= 0) {
            System.out.println("Резюме c uuid " + resume.getUuid() + " уже есть в списке");
            return;
        }
        storage[position++] = resume;
    }

    Resume get(String uuid) {
        int indexOfResume = getIndexOfResume(uuid);
        if (indexOfResume >= 0) {
            return storage[indexOfResume];
        }
        return null;
    }

    void update(Resume resume) {
        int indexOfResume = getIndexOfResume(resume.getUuid());
        if (indexOfResume >= 0) {
            storage[indexOfResume] = resume;
        }
    }

    void delete(String Uuid) {
        int indexOfResume = getIndexOfResume(Uuid);
        if (indexOfResume >= 0) {
            storage[indexOfResume] = storage[position - 1];
            storage[position - 1] = null;
            position--;
            return;
        }
    }

    private int getIndexOfResume(String Uuid) {
        for (int i = 0; i < position; i++) {
            if (storage[i].getUuid().equals(Uuid)) {
                return i;
            }
        }
        System.out.println("Резюме c uuid " + Uuid + " отсутствует в списке");
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, position);
    }

    int size() {
        return position;
    }
}
