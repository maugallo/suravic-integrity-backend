package edu.usal.suravicIntegrity.meatDetails;

import org.springframework.stereotype.Service;

@Service
public class MeatDetailsService {

    private final MeatDetailsRepository meatDetailsRepository;

    public MeatDetailsService(MeatDetailsRepository meatDetailsRepository) {
        this.meatDetailsRepository = meatDetailsRepository;
    }

    // CREATE METHOD:
    public String addMeatDetails(MeatDetails meatDetails) {
        meatDetailsRepository.save(meatDetails);

        return "Detalles de carne agregados correctamente";
    }
    
}
