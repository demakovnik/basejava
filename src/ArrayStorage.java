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
        for (int i = 0; i < position; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                System.out.println("Резюме c uuid " + resume.getUuid() + " уже есть в списке");
                return;
            }
        }
        storage[position++] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < position; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
        return null;
    }

    void update(Resume requiredResume, Resume resume) {
        for (int i = 0; i < position; i++) {
            if (storage[i].getUuid().equals(requiredResume.getUuid())) {
                storage[i] = resume;
                return;
            }
        }
        System.out.println("Резюме c uuid " + requiredResume.getUuid() + " отсутствует в списке");
    }

    void delete(String uuid) {
        for (int i = 0; i < position; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[position - 1];
                storage[position - 1] = null;
                position--;
                return;
            }
        }
        System.out.println("Резюме c uuid " + uuid + " отсутствует в списке");
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
