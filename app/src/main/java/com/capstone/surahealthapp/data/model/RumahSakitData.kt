package com.capstone.surahealthapp.data.model

object RumahSakitData {
    val rumahSakit = listOf(
        RumahSakit(
            1,
            "Rumah Sakit Kucing",
            "https://pariwisatasolo.surakarta.go.id/wp-content/uploads/2019/11/rsud.jpg",
            4,
            "A",
            -7.554253663652859, 110.77247558444483
        ),
        RumahSakit(
            2,
            "Rumah Sakit Anjing",
            "data:image/jpeg;base64,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",
            4,
            "A",
            -7.55765708012214, 110.83976684685943
        ),
        RumahSakit(
            3,
            "Rumah Sakit Monyet",
            "data:image/jpeg;base64,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",
            4,
            "A",
            -7.5437029022691755, 110.78002868532812
        ),
        RumahSakit(
            4,
            "Rumah Sakit Macan",
            "data:image/jpeg;base64,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",
            5,
            "8",
            -7.5527221174940165, 110.83856521717347
        ),
        RumahSakit(
            5,
            "Rumah Sakit Burung",
            "https://pariwisatasolo.surakarta.go.id/wp-content/uploads/2019/11/rsud.jpg",
            100,
            "Z",
            -7.60904540167155, 110.80800949087296
        ),
    )
}