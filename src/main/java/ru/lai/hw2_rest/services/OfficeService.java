package ru.lai.hw2_rest.services;

import org.springframework.stereotype.Service;
import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.repositories.OfficeRepository;

@Service
public class OfficeService extends AbstractServiceImpl<Office> {

    public OfficeService(OfficeRepository repository) {
        super(repository);
    }
}
