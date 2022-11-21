package it.polimi.gabrielegiusti.Factories;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import it.polimi.gabrielegiusti.Adapters.*;
import it.polimi.gabrielegiusti.Models.*;

public class ScientificArticleTypeAdapterFactory  implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {

        final Class<? super T> rawClass = typeToken.getRawType();

        if (ScientificArticle.class.isAssignableFrom(rawClass)) return ScientificArticleAdapter.get(rawClass, gson);

        if (Author.class.isAssignableFrom(rawClass)) return AuthorAdapter.get(rawClass, gson);

        if (Affiliation.class.isAssignableFrom(rawClass)) return AffiliationAdapter.get(rawClass, gson);

        if (Location.class.isAssignableFrom(rawClass)) return LocationAdapter.get(rawClass, gson);

        if (Section.class.isAssignableFrom(rawClass)) return SectionAdapter.get(rawClass, gson);

        if (PublicationDetails.class.isAssignableFrom(rawClass)) return PublicationDetailsAdapter.get(rawClass, gson);

        if (Subsection.class.isAssignableFrom(rawClass)) return SubsectionAdapter.get(rawClass, gson);

        if (Paragraph.class.isAssignableFrom(rawClass)) return ParagraphAdapter.get(rawClass, gson);

        if (Reference.class.isAssignableFrom(rawClass)) return ReferenceAdapter.get(rawClass, gson);

        if (Figure.class.isAssignableFrom(rawClass)) return FigureAdapter.get(rawClass, gson);

        return null;
    }
}
